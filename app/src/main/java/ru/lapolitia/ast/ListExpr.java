package ru.lapolitia.ast;

import java.util.List;
import ru.lapolitia.semantic.AstVisitor;

public record ListExpr(List<Expression> elements) implements Expression {
    public ListExpr {
        elements = List.copyOf(elements);
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitListExpr(this);
    }
}
