package ru.lapolitia.ast;

import ru.lapolitia.semantic.AstTransformer;
import ru.lapolitia.semantic.AstVisitor;

public record Return(Expression value) implements Expression {
    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitReturn(this);
    }

    @Override
    public Expression accept(AstTransformer transformer) {
        return transformer.transformReturn(this);
    }
}
