package ru.lapolitia.ast;

import ru.lapolitia.semantic.AstTransformer;
import ru.lapolitia.semantic.AstVisitor;

public sealed interface Expression
        permits Program,
                Identifier,
                NumberLiteral,
                BooleanLiteral,
                NullLiteral,
                ListExpr,
                QuoteExpr,
                SetQ,
                Func,
                Lambda,
                Prog,
                Cond,
                While,
                Return,
                Break {
    void accept(AstVisitor visitor);

    Expression accept(AstTransformer transformer);
}
