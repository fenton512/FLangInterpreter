package ru.lapolitia.ast;

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
                Break {}
