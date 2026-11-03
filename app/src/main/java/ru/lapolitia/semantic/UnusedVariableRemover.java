package ru.lapolitia.semantic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ru.lapolitia.ast.BooleanLiteral;
import ru.lapolitia.ast.Break;
import ru.lapolitia.ast.Cond;
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
import ru.lapolitia.ast.Return;
import ru.lapolitia.ast.SetQ;
import ru.lapolitia.ast.While;

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
        if (!isSequence(new ListExpr(transformed))) {
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
        return expressions.stream()
                .filter(
                        expression -> {
                            if (!(expression instanceof SetQ setQ)
                                    || usedNames.contains(setQ.name().name())
                                    || !isSafeValue(setQ.value())) {
                                return true;
                            }
                            int index = expressions.indexOf(expression);
                            return index == expressions.size() - 1;
                        })
                .toList();
    }

    private boolean isSafeValue(Expression expression) {
        return expression instanceof NumberLiteral
                || expression instanceof BooleanLiteral
                || expression instanceof NullLiteral
                || expression instanceof QuoteExpr;
    }

    private boolean isSequence(ListExpr expression) {
        if (expression.elements().isEmpty()) {
            return false;
        }
        Expression first = expression.elements().get(0);
        return first instanceof Break
                || first instanceof Cond
                || first instanceof Func
                || first instanceof Lambda
                || first instanceof ListExpr
                || first instanceof Prog
                || first instanceof Return
                || first instanceof SetQ
                || first instanceof While;
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
        public void visitQuoteExpr(QuoteExpr expression) {}
    }
}
