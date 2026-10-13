package ru.lapolitia.ast;

public record While(Expression condition, Expression body) implements Expression {}
