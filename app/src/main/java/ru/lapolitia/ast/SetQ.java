package ru.lapolitia.ast;

public record SetQ(Identifier name, Expression value) implements Expression {}
