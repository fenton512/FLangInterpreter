package ru.lapolitia.ast;

public record Prog(ListExpr locals, ListExpr body) implements Expression {}
