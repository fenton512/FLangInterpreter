package ru.lapolitia.interpreter;

import java.util.List;

/** Factories and primitive operations shared by the interpreter and built-in functions. */
public final class Values {
    public static final NullValue NULL = NullValue.INSTANCE;
    public static final BoolValue TRUE = new BoolValue(true);
    public static final BoolValue FALSE = new BoolValue(false);

    private Values() {}

    public static BoolValue bool(boolean value) {
        return value ? TRUE : FALSE;
    }

    public static Value number(double value, boolean isInt) {
        return isInt ? new IntValue((long) value) : new RealValue(value);
    }

    public static Value list(List<Value> elements) {
        return elements.isEmpty() ? NULL : new ListValue(elements);
    }

    public static boolean isNumber(Value value) {
        return value instanceof IntValue || value instanceof RealValue;
    }

    public static double asNumber(Value value, String operation) {
        if (value instanceof IntValue integer) {
            return integer.value();
        }
        if (value instanceof RealValue real) {
            return real.value();
        }
        throw new RuntimeError(operation + ": expected a number, got " + typeName(value));
    }

    public static boolean asBool(Value value, String operation) {
        if (value instanceof BoolValue bool) {
            return bool.value();
        }
        throw new RuntimeError(operation + ": expected a bool, got " + typeName(value));
    }

    public static Value arithmetic(String operation, Value left, Value right) {
        if (left instanceof IntValue leftInt && right instanceof IntValue rightInt) {
            long leftValue = leftInt.value();
            long rightValue = rightInt.value();
            if (operation.equals("divide") && rightValue == 0) {
                throw new RuntimeError("divide: division by zero");
            }
            long result =
                    switch (operation) {
                        case "plus" -> leftValue + rightValue;
                        case "minus" -> leftValue - rightValue;
                        case "times" -> leftValue * rightValue;
                        case "divide" -> leftValue / rightValue;
                        default ->
                                throw new IllegalArgumentException(
                                        "unknown operation: " + operation);
                    };
            return new IntValue(result);
        }

        double leftValue = asNumber(left, operation);
        double rightValue = asNumber(right, operation);
        if (operation.equals("divide") && rightValue == 0.0) {
            throw new RuntimeError("divide: division by zero");
        }
        double result =
                switch (operation) {
                    case "plus" -> leftValue + rightValue;
                    case "minus" -> leftValue - rightValue;
                    case "times" -> leftValue * rightValue;
                    case "divide" -> leftValue / rightValue;
                    default ->
                            throw new IllegalArgumentException("unknown operation: " + operation);
                };
        return new RealValue(result);
    }

    public static int compare(Value left, Value right, String operation) {
        if (left instanceof IntValue leftInt && right instanceof IntValue rightInt) {
            return Long.compare(leftInt.value(), rightInt.value());
        }
        if (left instanceof BoolValue leftBool && right instanceof BoolValue rightBool) {
            return Boolean.compare(leftBool.value(), rightBool.value());
        }
        if (isNumber(left) && isNumber(right)) {
            return Double.compare(asNumber(left, operation), asNumber(right, operation));
        }
        throw new RuntimeError(
                operation
                        + ": expected two numbers, got "
                        + typeName(left)
                        + " and "
                        + typeName(right));
    }

    public static boolean equal(Value left, Value right) {
        if (left == right) {
            return true;
        }
        if (left instanceof IntValue leftInt) {
            if (right instanceof IntValue rightInt) {
                return leftInt.value() == rightInt.value();
            }
            if (right instanceof RealValue rightReal) {
                return leftInt.value() == rightReal.value();
            }
            return false;
        }
        if (left instanceof RealValue leftReal) {
            if (right instanceof IntValue rightInt) {
                return leftReal.value() == rightInt.value();
            }
            if (right instanceof RealValue rightReal) {
                return leftReal.value() == rightReal.value();
            }
            return false;
        }
        if (left instanceof ListValue leftList && right instanceof ListValue rightList) {
            List<Value> leftElements = leftList.elements();
            List<Value> rightElements = rightList.elements();
            if (leftElements.size() != rightElements.size()) {
                return false;
            }
            for (int index = 0; index < leftElements.size(); index++) {
                if (!equal(leftElements.get(index), rightElements.get(index))) {
                    return false;
                }
            }
            return true;
        }
        if (left instanceof Callable || right instanceof Callable) {
            return false;
        }
        return left.equals(right);
    }

    public static String typeName(Value value) {
        if (value instanceof IntValue) {
            return "int";
        }
        if (value instanceof RealValue) {
            return "real";
        }
        if (value instanceof BoolValue) {
            return "bool";
        }
        if (value instanceof NullValue) {
            return "null";
        }
        if (value instanceof ListValue) {
            return "list";
        }
        if (value instanceof SymbolValue) {
            return "symbol";
        }
        if (value instanceof ClosureValue) {
            return "closure";
        }
        if (value instanceof BuiltinValue) {
            return "builtin";
        }
        if (value instanceof CodeValue) {
            return "code";
        }
        throw new IllegalArgumentException("unknown value: " + value);
    }
}
