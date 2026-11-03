package ru.lapolitia.ast;

import ru.lapolitia.semantic.AstTransformer;
import ru.lapolitia.semantic.AstVisitor;

public record While(Expression condition, Expression body) implements Expression {
    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitWhile(this);
    }

    @Override
    public Expression accept(AstTransformer transformer) {
        return transformer.transformWhile(this);
    }
}
