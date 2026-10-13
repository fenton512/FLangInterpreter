package ru.lapolitia.ast;

public record Func(Identifier name, ListExpr parameters, Expression body) implements Expression {}
