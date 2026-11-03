package ru.lapolitia.ast;

import java.util.Objects;
import ru.lapolitia.semantic.AstTransformer;
import ru.lapolitia.semantic.AstVisitor;

public record QuoteExpr(Expression expression) implements Expression {
    public QuoteExpr {
        Objects.requireNonNull(expression);
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitQuoteExpr(this);
    }

    @Override
    public Expression accept(AstTransformer transformer) {
        return transformer.transformQuoteExpr(this);
    }
}
