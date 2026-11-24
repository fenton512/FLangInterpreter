package ru.lapolitia.semantic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ru.lapolitia.ast.AstShapes;
import ru.lapolitia.ast.BooleanLiteral;
import ru.lapolitia.ast.Expression;
import ru.lapolitia.ast.Func;
import ru.lapolitia.ast.Identifier;
import ru.lapolitia.ast.Lambda;
import ru.lapolitia.ast.ListExpr;
import ru.lapolitia.ast.NullLiteral;
import ru.lapolitia.ast.NumberLiteral;
import ru.lapolitia.ast.Prog;
import ru.lapolitia.ast.Program;
import ru.lapolitia.ast.QuoteExpr;
import ru.lapolitia.ast.SetQ;

public final class UnusedVariableRemover extends AbstractAstTransformer {
    private Set<String> usedNames = Set.of();

    @Override
    public Expression transformProgram(Program expression) {
        usedNames = collectUsedNames(expression);
        List<Expression> transformed =
                expression.expressions().stream().map(this::transform).toList();
        return new Program(filterSequence(transformed));
    }

    @Override
    public Expression transformListExpr(ListExpr expression) {
        List<Expression> transformed = expression.elements().stream().map(this::transform).toList();
        if (AstShapes.isApplication(new ListExpr(transformed))) {
            return new ListExpr(transformed);
        }
        return new ListExpr(filterSequence(transformed));
    }

    @Override
    public Expression transformProg(Prog expression) {
        List<Expression> transformedLocals =
                expression.locals().elements().stream().map(this::transform).toList();
        ListExpr body = (ListExpr) transform(expression.body());
        List<Expression> remainingLocals =
                transformedLocals.stream()
                        .filter(
                                local ->
                                        !(local instanceof Identifier identifier)
                                                || usedNames.contains(identifier.name()))
                        .toList();
        return new Prog(new ListExpr(remainingLocals), body);
    }

    private List<Expression> filterSequence(List<Expression> expressions) {
        List<Expression> result = new ArrayList<>();
        for (int index = 0; index < expressions.size(); index++) {
            Expression expression = expressions.get(index);
            boolean last = index == expressions.size() - 1;
            if (expression instanceof SetQ setQ
                    && !usedNames.contains(setQ.name().name())
                    && isSafeValue(setQ.value())
                    && !last) {
                // Drop the assignment, but keep its value so the enclosing sequence keeps its
                // shape and result.
                result.add(setQ.value());
                continue;
            }
            result.add(expression);
        }
        return result;
    }

    private boolean isSafeValue(Expression expression) {
        return expression instanceof NumberLiteral
                || expression instanceof BooleanLiteral
                || expression instanceof NullLiteral
                || expression instanceof QuoteExpr;
    }

    private Set<String> collectUsedNames(Expression expression) {
        UsageCollector collector = new UsageCollector();
        expression.accept(collector);
        return collector.usedNames;
    }

    private static final class UsageCollector extends AbstractAstVisitor {
        private final Set<String> usedNames = new HashSet<>();

        @Override
        public void visitIdentifier(Identifier expression) {
            usedNames.add(expression.name());
        }

        @Override
        public void visitSetQ(SetQ expression) {
            expression.value().accept(this);
        }

        @Override
        public void visitFunc(Func expression) {
            expression.body().accept(this);
        }

        @Override
        public void visitLambda(Lambda expression) {
            expression.body().accept(this);
        }

        @Override
        public void visitProg(Prog expression) {
            expression.body().accept(this);
        }

        @Override
        public void visitQuoteExpr(QuoteExpr expression) {
            // A quoted identifier can still be referenced later through (eval 'name).
            expression.expression().accept(this);
        }
    }
}
