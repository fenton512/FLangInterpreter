package ru.lapolitia.semantic;

import java.util.List;
import ru.lapolitia.ast.BooleanLiteral;
import ru.lapolitia.ast.Expression;
import ru.lapolitia.ast.Identifier;
import ru.lapolitia.ast.ListExpr;
import ru.lapolitia.ast.NullLiteral;
import ru.lapolitia.ast.NumberLiteral;

public final class ExpressionSimplifier extends AbstractAstTransformer {
    @Override
    public Expression transformListExpr(ListExpr expression) {
        ListExpr transformed = (ListExpr) super.transformListExpr(expression);
        return simplify(transformed);
    }

    private Expression simplify(ListExpr expression) {
        List<Expression> elements = expression.elements();
        if (elements.isEmpty() || !(elements.get(0) instanceof Identifier identifier)) {
            return expression;
        }

        List<Expression> arguments = elements.subList(1, elements.size());
        return switch (identifier.name()) {
            case "plus" -> simplifyArithmetic(arguments, ArithmeticOperation.ADD, expression);
            case "minus" -> simplifyArithmetic(arguments, ArithmeticOperation.SUBTRACT, expression);
            case "times" -> simplifyArithmetic(arguments, ArithmeticOperation.MULTIPLY, expression);
            case "divide" -> simplifyArithmetic(arguments, ArithmeticOperation.DIVIDE, expression);
            case "equal" -> simplifyEquality(arguments, false, expression);
            case "nonequal" -> simplifyEquality(arguments, true, expression);
            case "less" -> simplifyComparison(arguments, ComparisonOperation.LESS, expression);
            case "lesseq" ->
                    simplifyComparison(arguments, ComparisonOperation.LESS_OR_EQUAL, expression);
            case "greater" ->
                    simplifyComparison(arguments, ComparisonOperation.GREATER, expression);
            case "greatereq" ->
                    simplifyComparison(arguments, ComparisonOperation.GREATER_OR_EQUAL, expression);
            case "and" -> simplifyLogical(arguments, LogicalOperation.AND, expression);
            case "or" -> simplifyLogical(arguments, LogicalOperation.OR, expression);
            case "xor" -> simplifyLogical(arguments, LogicalOperation.XOR, expression);
            case "not" -> simplifyNot(arguments, expression);
            default -> expression;
        };
    }

    private Expression simplifyArithmetic(
            List<Expression> arguments, ArithmeticOperation operation, Expression original) {
        if (arguments.size() != 2
                || !(arguments.get(0) instanceof NumberLiteral left)
                || !(arguments.get(1) instanceof NumberLiteral right)) {
            return original;
        }
        if (operation == ArithmeticOperation.DIVIDE && right.value() == 0.0) {
            return original;
        }

        if (left.isInt() && right.isInt()) {
            long leftValue = (long) left.value();
            long rightValue = (long) right.value();
            long result =
                    switch (operation) {
                        case ADD -> leftValue + rightValue;
                        case SUBTRACT -> leftValue - rightValue;
                        case MULTIPLY -> leftValue * rightValue;
                        case DIVIDE -> leftValue / rightValue;
                    };
            return new NumberLiteral(result, true);
        }

        double result =
                switch (operation) {
                    case ADD -> left.value() + right.value();
                    case SUBTRACT -> left.value() - right.value();
                    case MULTIPLY -> left.value() * right.value();
                    case DIVIDE -> left.value() / right.value();
                };
        return new NumberLiteral(result, false);
    }

    private Expression simplifyEquality(
            List<Expression> arguments, boolean negate, Expression original) {
        if (arguments.size() != 2) {
            return original;
        }

        Expression left = arguments.get(0);
        Expression right = arguments.get(1);
        Boolean equal = literalEquality(left, right);
        if (equal == null) {
            return original;
        }
        return new BooleanLiteral(negate ? !equal : equal);
    }

    private Expression simplifyComparison(
            List<Expression> arguments, ComparisonOperation operation, Expression original) {
        if (arguments.size() != 2
                || !(arguments.get(0) instanceof NumberLiteral left)
                || !(arguments.get(1) instanceof NumberLiteral right)) {
            return original;
        }

        boolean result =
                switch (operation) {
                    case LESS -> left.value() < right.value();
                    case LESS_OR_EQUAL -> left.value() <= right.value();
                    case GREATER -> left.value() > right.value();
                    case GREATER_OR_EQUAL -> left.value() >= right.value();
                };
        return new BooleanLiteral(result);
    }

    private Expression simplifyLogical(
            List<Expression> arguments, LogicalOperation operation, Expression original) {
        if (arguments.size() != 2
                || !(arguments.get(0) instanceof BooleanLiteral left)
                || !(arguments.get(1) instanceof BooleanLiteral right)) {
            return original;
        }

        boolean result =
                switch (operation) {
                    case AND -> left.value() && right.value();
                    case OR -> left.value() || right.value();
                    case XOR -> left.value() ^ right.value();
                };
        return new BooleanLiteral(result);
    }

    private Expression simplifyNot(List<Expression> arguments, Expression original) {
        if (arguments.size() != 1 || !(arguments.get(0) instanceof BooleanLiteral value)) {
            return original;
        }
        return new BooleanLiteral(!value.value());
    }

    private Boolean literalEquality(Expression left, Expression right) {
        if (left instanceof NumberLiteral leftNumber
                && right instanceof NumberLiteral rightNumber) {
            return leftNumber.value() == rightNumber.value();
        }
        if (left instanceof BooleanLiteral leftBoolean
                && right instanceof BooleanLiteral rightBoolean) {
            return leftBoolean.value() == rightBoolean.value();
        }
        if (left instanceof NullLiteral && right instanceof NullLiteral) {
            return true;
        }
        return null;
    }

    private enum ArithmeticOperation {
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE
    }

    private enum ComparisonOperation {
        LESS,
        LESS_OR_EQUAL,
        GREATER,
        GREATER_OR_EQUAL
    }

    private enum LogicalOperation {
        AND,
        OR,
        XOR
    }
}
