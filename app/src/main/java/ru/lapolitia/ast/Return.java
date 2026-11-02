package ru.lapolitia.ast;

import ru.lapolitia.semantic.AstVisitor;

public record Return(Expression value) implements Expression {
    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitReturn(this);
    }
}
