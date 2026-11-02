package ru.lapolitia.ast;

import ru.lapolitia.semantic.AstVisitor;

public record Break() implements Expression {
    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitBreak(this);
    }
}
