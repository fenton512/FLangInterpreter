package ru.lapolitia.ast;

import java.util.Optional;
import ru.lapolitia.semantic.AstVisitor;

public record Cond(Expression condition, Expression consequent, Optional<Expression> alternative)
        implements Expression {
    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitCond(this);
    }
}
