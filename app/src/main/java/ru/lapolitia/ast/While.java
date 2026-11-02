package ru.lapolitia.ast;

import ru.lapolitia.semantic.AstVisitor;

public record While(Expression condition, Expression body) implements Expression {
    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitWhile(this);
    }
}
