package ru.lapolitia.interpreter.builtin;

import java.util.List;
import ru.lapolitia.interpreter.Environment;
import ru.lapolitia.interpreter.value.Value;

@FunctionalInterface
public interface BuiltinFunction {
    Value invoke(List<Value> arguments, Environment environment);
}
