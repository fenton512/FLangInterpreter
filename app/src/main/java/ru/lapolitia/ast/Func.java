package ru.lapolitia.ast;

import ru.lapolitia.semantic.AstVisitor;

public record Func(Identifier name, ListExpr parameters, Expression body) implements Expression {
    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitFunc(this);
    }
}
