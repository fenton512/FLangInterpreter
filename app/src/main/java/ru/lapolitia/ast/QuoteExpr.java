package ru.lapolitia.ast;

import java.util.Objects;

public record QuoteExpr(Expression expression) implements Expression {
    public QuoteExpr {
        Objects.requireNonNull(expression);
    }
}
