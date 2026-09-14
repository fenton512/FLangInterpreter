public class Token {
    final TokenType type;
    final String source;
    final Object literal;
    final int line;
    final int column;
    final int length;


    public Token(TokenType type, String source, Object literal, int line) {
        this.type = type;
        this.source = source;
        this.literal = literal;
        this.line = line;
        this.column = 0;
        this.length = 0;
    }


    public String toString() {
        return type + " " + source + " " + literal;
    }
}
