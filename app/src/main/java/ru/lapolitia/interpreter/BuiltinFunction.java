package ru.lapolitia.interpreter;

import java.util.List;

@FunctionalInterface
public interface BuiltinFunction {
    Value invoke(List<Value> arguments, Environment environment);
}
