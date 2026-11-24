package ru.lapolitia.interpreter;

public final class RuntimeError extends RuntimeException {
    public RuntimeError(String message) {
        super(message);
    }
}
