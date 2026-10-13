package ru.lapolitia.ast;

import java.util.List;

public record Program(List<Expression> expressions) implements Expression {
    public Program {
        expressions = List.copyOf(expressions);
    }
}
