package ru.lapolitia.interpreter;

import java.util.List;
import java.util.Objects;

public record BuiltinValue(String name, int arity, BuiltinFunction function)
        implements Value, Callable {
    public BuiltinValue {
        Objects.requireNonNull(name);
        Objects.requireNonNull(function);
        if (arity < 0) {
            throw new IllegalArgumentException("arity must not be negative");
        }
    }

    public Value invoke(List<Value> arguments, Environment environment) {
        return function.invoke(arguments, environment);
    }
}
