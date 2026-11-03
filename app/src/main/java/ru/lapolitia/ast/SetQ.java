package ru.lapolitia.ast;

import ru.lapolitia.semantic.AstTransformer;
import ru.lapolitia.semantic.AstVisitor;

public record SetQ(Identifier name, Expression value) implements Expression {
    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitSetQ(this);
    }

    @Override
    public Expression accept(AstTransformer transformer) {
        return transformer.transformSetQ(this);
    }
}
