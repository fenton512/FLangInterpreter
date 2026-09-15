package ru.lapolitia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();

    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("quote", TokenType.QUOTE);
        keywords.put("setq", TokenType.SETQ);
        keywords.put("func", TokenType.FUNC);
        keywords.put("lambda", TokenType.LAMBDA);
        keywords.put("prog", TokenType.PROG);
        keywords.put("cond", TokenType.COND);
        keywords.put("while", TokenType.WHILE);
        keywords.put("return", TokenType.RETURN);
        keywords.put("break", TokenType.BREAK);
        keywords.put("plus", TokenType.OP_PLUS);
        keywords.put("minus", TokenType.OP_MINUS);
        keywords.put("times", TokenType.OP_TIMES);
        keywords.put("divide", TokenType.OP_DIVIDE);
        keywords.put("head", TokenType.HEAD);
        keywords.put("tail", TokenType.TAIL);
        keywords.put("cons", TokenType.CONS);
        keywords.put("equal", TokenType.EQUAL);
        keywords.put("nonequal", TokenType.NONEQUAL);
        keywords.put("less", TokenType.LESS);
        keywords.put("lesseq", TokenType.LESSEQ);
        keywords.put("greater", TokenType.GREATER);
        keywords.put("greatereq", TokenType.GREATEREQ);
        keywords.put("isint", TokenType.ISINT);
        keywords.put("isreal", TokenType.ISREAL);
        keywords.put("isbool", TokenType.ISBOOL);
        keywords.put("isnull", TokenType.ISNULL);
        keywords.put("isatom", TokenType.ISATOM);
        keywords.put("islist", TokenType.ISLIST);
        keywords.put("and", TokenType.AND);
        keywords.put("or", TokenType.OR);
        keywords.put("xor", TokenType.XOR);
        keywords.put("not", TokenType.NOT);
        keywords.put("eval", TokenType.EVAl);
        keywords.put("true", TokenType.TRUE);
        keywords.put("false", TokenType.FALSE);
        keywords.put("null", TokenType.NULL);
    }

    private int start = 0;
    private int current = 0;
    private int line = 1;

    public Scanner(String source) {
        this.source = source;
    }

    public List<Token> scanTokens() {
        while (!isEnd()) {
            // starts next lexeme
            start = current;
            scanToken();
        }
        tokens.add(new Token(TokenType.EOF, "", null, line));
        return tokens;
    }

    private boolean isEnd() {
        return current == source.length();
    }

    private void scanToken() {
        char ch = getNextChar();
        switch (ch) {
            case '(' -> addToken(TokenType.LEFT_PAREN);
            case ')' -> addToken(TokenType.RIGHT_PAREN);
            case '\'' -> addToken(TokenType.QUOTE_SYM);
            case '-', '+' -> {
                if (isDigit(skip())) {
                    getNextChar();
                    parceNumber();
                } else {
                    Flang.reportError(line, "Unexpected " + ch);
                }
            }
            case '/' -> {
                while (skip() != '\n' && !isEnd()) getNextChar();
            }

            case ' ', '\r', '\t' -> {}
            case '\n' -> ++line;

            default -> {
                if (isDigit(ch)) {
                    parceNumber();
                } else if (isLetter(ch)) {
                    parceIdentifier();
                } else {
                    Flang.reportError(line, "Unexpected character: " + ch);
                }
            }
        }
    }

    private void parceIdentifier() {
        while (isLetterDigit(skip())) {
            getNextChar();
        }

        String text = source.substring(start, current);
        TokenType type = keywords.get(text);
        if (type == null) {
            type = TokenType.IDENTIFIER;
        }

        if (type == TokenType.FALSE) {
            addToken(type, false);
        } else if (type == TokenType.TRUE) {
            addToken(type, true);
        } else {
            addToken(type);
        }
    }

    private boolean isLetter(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
    }

    private boolean isLetterDigit(char ch) {
        return isDigit(ch) || isLetter(ch);
    }

    private void parceNumber() {
        while (isDigit(skip())) {
            getNextChar();
        }

        if (skip() == '.' && isDigit(skipNext())) {
            getNextChar();
        } else if (isValidAfterDigit(skip())) {
            Flang.reportError(line, "Unexpected character '" + skip() + "' in number");
            addToken(TokenType.NUMBER, Double.parseDouble(source.substring(start, current)));
            getNextChar();
            return;
        }

        while (isDigit(skip())) {
            getNextChar();
        }
        if (isValidAfterDigit(skip())) {
            Flang.reportError(line, "Unexpected character '" + skip() + "' in number");
            addToken(TokenType.NUMBER, Double.parseDouble(source.substring(start, current)));
            getNextChar();
            return;
        }

        addToken(TokenType.NUMBER, Double.parseDouble(source.substring(start, current)));
    }

    private Character skipNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private Boolean isDigit(Character ch) {
        return '0' <= ch && ch <= '9';
    }

    private boolean isValidAfterDigit(Character ch) {
        return ch != '(' && ch != ')' && ch != ' ' && ch != '\n' && ch != '\0';
    }

    private Character skip() {
        if (isEnd()) return '\0';
        return source.charAt(current);
    }

    private Character getNextChar() {
        return source.charAt(current++);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
}
