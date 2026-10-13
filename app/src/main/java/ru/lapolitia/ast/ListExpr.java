package ru.lapolitia.ast;

import java.util.List;

public record ListExpr(List<Expression> elements) implements Expression {
    public ListExpr {
        elements = List.copyOf(elements);
    }
}
