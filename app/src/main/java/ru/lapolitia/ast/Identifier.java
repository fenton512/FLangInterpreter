package ru.lapolitia.ast;

import java.util.Objects;
import ru.lapolitia.semantic.AstTransformer;
import ru.lapolitia.semantic.AstVisitor;

public record Identifier(String name) implements Expression {
    public Identifier {
        Objects.requireNonNull(name);
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitIdentifier(this);
    }

    @Override
    public Expression accept(AstTransformer transformer) {
        return transformer.transformIdentifier(this);
    }
}
