package ru.lapolitia.ast;

import java.util.Objects;

public record Identifier(String name) implements Expression {
    public Identifier {
        Objects.requireNonNull(name);
    }
}
