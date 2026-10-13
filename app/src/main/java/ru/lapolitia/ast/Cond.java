package ru.lapolitia.ast;

import java.util.Optional;

public record Cond(Expression condition, Expression consequent, Optional<Expression> alternative)
        implements Expression {}
