package ru.lapolitia.interpreter;

final class ReturnSignal extends RuntimeException {
    private final Value value;

    ReturnSignal(Value value) {
        super(null, null, false, false);
        this.value = value;
    }

    Value value() {
        return value;
    }
}
