package ru.lapolitia.ast;

import java.util.List;

public final class AstPrinter {
    private AstPrinter() {}

    public static String print(Expression expression) {
        StringBuilder output = new StringBuilder(label(expression));
        appendChildren(output, children(expression), "");
        return output.toString();
    }

    private static void appendChildren(
            StringBuilder output, List<Expression> children, String prefix) {
        for (int index = 0; index < children.size(); index++) {
            Expression child = children.get(index);
            boolean isLast = index == children.size() - 1;
            output.append(System.lineSeparator())
                    .append(prefix)
                    .append(isLast ? "\\-- " : "|-- ")
                    .append(label(child));
            appendChildren(output, children(child), prefix + (isLast ? "    " : "|   "));
        }
    }

    private static List<Expression> children(Expression expression) {
        if (expression instanceof Program program) return program.expressions();
        if (expression instanceof ListExpr list) return list.elements();
        if (expression instanceof QuoteExpr quote) return List.of(quote.expression());
        if (expression instanceof SetQ setQ) return List.of(setQ.name(), setQ.value());
        if (expression instanceof Func func) {
            return List.of(func.name(), func.parameters(), func.body());
        }
        if (expression instanceof Lambda lambda) {
            return List.of(lambda.parameters(), lambda.body());
        }
        if (expression instanceof Prog prog) return List.of(prog.locals(), prog.body());
        if (expression instanceof Cond cond) {
            return cond.alternative()
                    .map(alternative -> List.of(cond.condition(), cond.consequent(), alternative))
                    .orElseGet(() -> List.of(cond.condition(), cond.consequent()));
        }
        if (expression instanceof While whileForm) {
            return List.of(whileForm.condition(), whileForm.body());
        }
        if (expression instanceof Return returnForm) return List.of(returnForm.value());
        return List.of();
    }

    private static String label(Expression expression) {
        if (expression instanceof Identifier identifier) return "Identifier: " + identifier.name();
        if (expression instanceof NumberLiteral number) return "NumberLiteral: " + number.value();
        if (expression instanceof BooleanLiteral bool) return "BooleanLiteral: " + bool.value();
        if (expression instanceof NullLiteral) return "NullLiteral";
        if (expression instanceof Program) return "Program";
        if (expression instanceof ListExpr) return "ListExpr";
        if (expression instanceof QuoteExpr) return "QuoteExpr";
        if (expression instanceof SetQ) return "SetQ";
        if (expression instanceof Func) return "Func";
        if (expression instanceof Lambda) return "Lambda";
        if (expression instanceof Prog) return "Prog";
        if (expression instanceof Cond) return "Cond";
        if (expression instanceof While) return "While";
        if (expression instanceof Return) return "Return";
        if (expression instanceof Break) return "Break";
        throw new IllegalArgumentException("Unknown AST expression: " + expression.getClass());
    }
}
