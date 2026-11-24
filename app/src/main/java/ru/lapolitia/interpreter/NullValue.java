package ru.lapolitia.interpreter;

/** The single null value. An empty list is represented by the same value. */
public record NullValue() implements Value {
    public static final NullValue INSTANCE = new NullValue();
}
