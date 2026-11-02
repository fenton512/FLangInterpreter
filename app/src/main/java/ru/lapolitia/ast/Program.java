package ru.lapolitia.ast;

import java.util.List;
import ru.lapolitia.semantic.AstVisitor;

public record Program(List<Expression> expressions) implements Expression {
    public Program {
        expressions = List.copyOf(expressions);
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitProgram(this);
    }
}
