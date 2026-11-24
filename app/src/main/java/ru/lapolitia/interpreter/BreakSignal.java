package ru.lapolitia.interpreter;

final class BreakSignal extends RuntimeException {
    BreakSignal() {
        super(null, null, false, false);
    }
}
