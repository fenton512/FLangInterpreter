package ru.lapolitia.ast;

public sealed interface Expression
        permits Program,
                Identifier,
                NumberLiteral,
                BooleanLiteral,
                NullLiteral,
                ListExpr,
                QuoteExpr {}
