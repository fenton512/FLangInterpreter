package ru.lapolitia.lexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.lapolitia.main.Flang;
import ru.lapolitia.parser.FParser.Lexer;
import ru.lapolitia.parser.TokenMapper;

public class Scanner implements Lexer {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();

    private static final Map<String, TokenType> keywords;
    private static final TokenMapper mapper;

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
        keywords.put("eval", TokenType.EVAL);
        keywords.put("true", TokenType.TRUE);
        keywords.put("false", TokenType.FALSE);
        keywords.put("null", TokenType.NULL);
        mapper = new TokenMapper();
    }

    private int start = 0;
    private int current = 0;
    private int line = 1;
    private Object currentValue;

    public Scanner(String source) {
        this.source = source;
    }

    @Override
    public Object getLVal() {
        return currentValue;
    }

    @Override
    public int yylex() throws java.io.IOException {
        start = current;
        currentValue = null;

        if (isEnd()) return mapper.mapToParser(TokenType.EOF);

        char ch = getNextChar();
        switch (ch) {
            case '(' -> {
                addToken(TokenType.LEFT_PAREN);
                return mapper.mapToParser(TokenType.LEFT_PAREN);
            }
            case ')' -> {
                addToken(TokenType.RIGHT_PAREN);
                return mapper.mapToParser(TokenType.RIGHT_PAREN);
            }
            case '\'' -> {
                addToken(TokenType.QUOTE_SYM);
                return mapper.mapToParser(TokenType.QUOTE_SYM);
            }
            case '-', '+' -> {
                if (isDigit(skip())) {
                    getNextChar();
                    return parceNumber();
                } else {
                    yyerror("Error in line " + line + " detail: Unexpected " + ch);
                    return Lexer.YYerror;
                }
            }
            case '/' -> {
                while (skip() != '\n' && !isEnd()) getNextChar();
                return yylex();
            }

            case ' ', '\r', '\t' -> {
                while (skip() == ' ' || skip() == '\r' || skip() == '\t' && !isEnd()) getNextChar();
                return yylex();
            }

            case '\n' -> {
                ++line;
                return yylex();
            }

            default -> {
                if (isDigit(ch)) {
                    return parceNumber();
                } else if (isLetter(ch) || isSpecialChar(ch)) {
                    return parceIdentifier();
                } else {
                    yyerror("Error in line " + line + " detail: Unexpected character: " + ch);
                    return Lexer.YYerror;
                }
            }
        }
    }

    @Override
    public void yyerror(String msg) {
        Flang.reportError(msg);
    }

    private boolean isEnd() {
        return current == source.length();
    }

    private int parceIdentifier() {
        while (isIdentifierChar(skip())) {
            getNextChar();
        }

        String text = source.substring(start, current);
        TokenType type = keywords.get(text);
        if (type == null) {
            type = TokenType.IDENTIFIER;
        }

        if (type == TokenType.FALSE) {
            currentValue = false;
            addToken(type, false);
            return mapper.mapToParser(TokenType.FALSE);
        } else if (type == TokenType.TRUE) {
            currentValue = true;
            addToken(type, true);
            return mapper.mapToParser(TokenType.TRUE);
        } else if (type == TokenType.IDENTIFIER) {
            currentValue = text;
            addToken(type, text);
            return mapper.mapToParser(type);
        } else {
            addToken(type);
            return mapper.mapToParser(type);
        }
    }

    private boolean isLetter(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
    }

    private boolean isSpecialChar(char ch) {
        return ch == '_' || ch == '-' || ch == '?' || ch == '!';
    }

    private boolean isIdentifierChar(char ch) {
        return isDigit(ch) || isLetter(ch) || isSpecialChar(ch);
    }

    private int parceNumber() {
        while (isDigit(skip())) {
            getNextChar();
        }

        if (skip() == '.' && isDigit(skipNext())) {
            getNextChar();
        } else if (isInvalidAfterDigit(skip())) {
            yyerror(
                    "Error in line "
                            + line
                            + " detail: Unexpected character '"
                            + skip()
                            + "' in number");
            currentValue = Double.parseDouble(source.substring(start, current));
            addToken(TokenType.NUMBER, currentValue);
            getNextChar();
            return Lexer.YYerror;
        }

        while (isDigit(skip())) {
            getNextChar();
        }
        if (isInvalidAfterDigit(skip())) {
            yyerror(
                    "Error in line "
                            + line
                            + " detail: Unexpected character '"
                            + skip()
                            + "' in number");
            currentValue = Double.parseDouble(source.substring(start, current));
            addToken(TokenType.NUMBER, currentValue);
            getNextChar();
            return Lexer.YYerror;
        }

        currentValue = Double.parseDouble(source.substring(start, current));
        addToken(TokenType.NUMBER, currentValue);
        return mapper.mapToParser(TokenType.NUMBER);
    }

    private Character skipNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private Boolean isDigit(Character ch) {
        return '0' <= ch && ch <= '9';
    }

    private boolean isInvalidAfterDigit(Character ch) {
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
