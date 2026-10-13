package ru.lapolitia.parser;

import ru.lapolitia.lexer.TokenType;
import ru.lapolitia.parser.FParser.Lexer;

public class TokenMapper {

    public TokenMapper() {}

    public int mapToParser(TokenType type) {
        return switch (type) {
            case QUOTE -> Lexer.QUOTE;
            case SETQ -> Lexer.SETQ;
            case FUNC -> Lexer.FUNC;
            case LAMBDA -> Lexer.LAMBDA;
            case PROG -> Lexer.PROG;
            case COND -> Lexer.COND;
            case WHILE -> Lexer.WHILE;
            case RETURN -> Lexer.RETURN;
            case BREAK -> Lexer.BREAK;
            case EVAL -> Lexer.EVAL;

            case LEFT_PAREN -> Lexer.LEFT_PAREN;
            case RIGHT_PAREN -> Lexer.RIGHT_PAREN;
            case QUOTE_SYM -> Lexer.QUOTE_SYM;

            case NULL -> Lexer.NULL;
            case NUMBER -> Lexer.NUMBER;
            case IDENTIFIER -> Lexer.IDENTIFIER;
            case TRUE -> Lexer.TRUE;
            case FALSE -> Lexer.FALSE;

            case OP_PLUS -> Lexer.OP_PLUS;
            case OP_MINUS -> Lexer.OP_MINUS;
            case OP_TIMES -> Lexer.OP_TIMES;
            case OP_DIVIDE -> Lexer.OP_DIVIDE;
            case HEAD -> Lexer.HEAD;
            case TAIL -> Lexer.TAIL;
            case CONS -> Lexer.CONS;
            case EQUAL -> Lexer.EQUAL;
            case NONEQUAL -> Lexer.NONEQUAL;
            case LESS -> Lexer.LESS;
            case LESSEQ -> Lexer.LESSEQ;
            case GREATER -> Lexer.GREATER;
            case GREATEREQ -> Lexer.GREATEREQ;

            case ISINT -> Lexer.ISINT;
            case ISREAL -> Lexer.ISREAL;
            case ISBOOL -> Lexer.ISBOOL;
            case ISNULL -> Lexer.ISNULL;
            case ISATOM -> Lexer.ISATOM;
            case ISLIST -> Lexer.ISLIST;

            case AND -> Lexer.AND;
            case OR -> Lexer.OR;
            case XOR -> Lexer.XOR;
            case NOT -> Lexer.NOT;

            case EOF -> Lexer.YYEOF;
        };
    }
}
