package ru.lapolitia.interpreter.value;

import java.util.Objects;

/** An identifier used as data, for example the result of {@code 'x} or {@code '(plus 1 2)}. */
public record SymbolValue(String name) implements Value {
    public SymbolValue {
        Objects.requireNonNull(name);
    }
}
