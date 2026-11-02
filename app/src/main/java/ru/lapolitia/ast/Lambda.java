package ru.lapolitia.ast;

import ru.lapolitia.semantic.AstVisitor;

public record Lambda(ListExpr parameters, Expression body) implements Expression {
    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitLambda(this);
    }
}
