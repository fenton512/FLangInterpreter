package ru.lapolitia;

public class Token {
    final TokenType type;
    final String source;
    final Object literal;
    final int line;
    final int column;
    final int length;

    public Token(TokenType type, String source, Object literal, int line) {
        this.type = type;
        this.source = source; this.literal = literal; this.line = line; this.column = 0;
        this.length = 0;
    }

    public Object getLiteral() {
        if (type == TokenType.NULL) {
            return null;
        } else if (literal == null) {
            return "LIT_UNSPEC";
        }
        return literal;
    }

    public String toString() {
        return type + " " + source + " " + getLiteral();
    }
}
