package ru.lapolitia.lexer;

/**
 * Number literal produced by the lexer. It keeps the distinction between integer and real literals,
 * because number literals are stored as {@code double} in the AST.
 */
public record NumberToken(double value, boolean isInt) {}
