package ru.lapolitia.ast;

public record Lambda(ListExpr parameters, Expression body) implements Expression {}
