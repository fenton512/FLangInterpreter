package ru.lapolitia.ast;

import ru.lapolitia.semantic.AstVisitor;

public record NullLiteral() implements Expression {
    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitNullLiteral(this);
    }
}
