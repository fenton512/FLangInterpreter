package ru.lapolitia.ast;

import ru.lapolitia.semantic.AstTransformer;
import ru.lapolitia.semantic.AstVisitor;

public record NumberLiteral(double value) implements Expression {
    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitNumberLiteral(this);
    }

    @Override
    public Expression accept(AstTransformer transformer) {
        return transformer.transformNumberLiteral(this);
    }
}
