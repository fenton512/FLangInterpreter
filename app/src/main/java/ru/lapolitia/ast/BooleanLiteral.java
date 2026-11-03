package ru.lapolitia.ast;

import ru.lapolitia.semantic.AstTransformer;
import ru.lapolitia.semantic.AstVisitor;

public record BooleanLiteral(boolean value) implements Expression {
    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitBooleanLiteral(this);
    }

    @Override
    public Expression accept(AstTransformer transformer) {
        return transformer.transformBooleanLiteral(this);
    }
}
