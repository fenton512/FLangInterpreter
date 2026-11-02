package ru.lapolitia.ast;

import ru.lapolitia.semantic.AstVisitor;

public record SetQ(Identifier name, Expression value) implements Expression {
    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitSetQ(this);
    }
}
