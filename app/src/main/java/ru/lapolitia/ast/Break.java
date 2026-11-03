package ru.lapolitia.ast;

import ru.lapolitia.semantic.AstTransformer;
import ru.lapolitia.semantic.AstVisitor;

public record Break() implements Expression {
    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitBreak(this);
    }

    @Override
    public Expression accept(AstTransformer transformer) {
        return transformer.transformBreak(this);
    }
}
