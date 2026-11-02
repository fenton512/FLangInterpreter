package ru.lapolitia.ast;

import ru.lapolitia.semantic.AstVisitor;

public record Prog(ListExpr locals, ListExpr body) implements Expression {
    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitProg(this);
    }
}
