package ru.lapolitia.ast;

import java.util.List;

/** Shared structural rules that decide how a {@link ListExpr} is interpreted. */
public final class AstShapes {
    private AstShapes() {}

    /**
     * A list is an application when its head is an identifier (a named function or a variable
     * holding a callable) or a lambda (an immediately applied anonymous function). Every other list
     * is a sequence: its elements are evaluated from left to right and the sequence value is the
     * list of their values.
     */
    public static boolean isApplication(ListExpr expression) {
        List<Expression> elements = expression.elements();
        if (elements.isEmpty()) {
            return false;
        }
        Expression head = elements.get(0);
        return head instanceof Identifier || head instanceof Lambda;
    }

    public static boolean isSequence(ListExpr expression) {
        return !isApplication(expression);
    }

    public static List<String> parameterNames(ListExpr parameters) {
        return parameters.elements().stream()
                .map(parameter -> ((Identifier) parameter).name())
                .toList();
    }
}
