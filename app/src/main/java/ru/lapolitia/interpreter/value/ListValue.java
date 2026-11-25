package ru.lapolitia.interpreter.value;

import java.util.List;

/** A non-empty list. The empty list is {@link NullValue}. */
public record ListValue(List<Value> elements) implements Value {
    public ListValue {
        elements = List.copyOf(elements);
        if (elements.isEmpty()) {
            throw new IllegalArgumentException("an empty list must be represented by null");
        }
    }
}
