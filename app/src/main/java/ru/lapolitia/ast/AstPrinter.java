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
        throw new IllegalArgumentException("Unknown AST expression: " + expression.getClass());
    }
}
