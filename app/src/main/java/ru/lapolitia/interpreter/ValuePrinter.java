package ru.lapolitia.interpreter;

import java.util.StringJoiner;

public final class ValuePrinter {
    private ValuePrinter() {}

    public static String format(Value value) {
        if (value instanceof IntValue integer) {
            return Long.toString(integer.value());
        }
        if (value instanceof RealValue real) {
            return formatReal(real.value());
        }
        if (value instanceof BoolValue bool) {
            return Boolean.toString(bool.value());
        }
        if (value instanceof NullValue) {
            return "null";
        }
        if (value instanceof SymbolValue symbol) {
            return symbol.name();
        }
        if (value instanceof ListValue list) {
            StringJoiner joiner = new StringJoiner(" ", "(", ")");
            list.elements().forEach(element -> joiner.add(format(element)));
            return joiner.toString();
        }
        if (value instanceof ClosureValue closure) {
            return closure.name();
        }
        if (value instanceof BuiltinValue builtin) {
            return "<builtin " + builtin.name() + ">";
        }
        if (value instanceof CodeValue) {
            return "<code>";
        }
        throw new IllegalArgumentException("unknown value: " + value);
    }

    private static String formatReal(double value) {
        if (Double.isFinite(value) && value == Math.rint(value) && Math.abs(value) < 1e15) {
            return Long.toString((long) value);
        }
        return Double.toString(value);
    }
}
