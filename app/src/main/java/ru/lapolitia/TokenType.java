package ru.lapolitia;

public enum TokenType {
    QUOTE,
    SETQ,
    FUNC,
    LAMBDA,
    PROG,
    COND,
    WHILE,
    RETURN,
    BREAK,
    EVAl,

    LEFT_PAREN,
    RIGHT_PAREN,
    QUOTE_SYM,

    NULL,
    NUMBER,
    IDENTIFIER,
    TRUE,
    FALSE,

    OP_PLUS,
    OP_MINUS,
    OP_TIMES,
    OP_DIVIDE,
    HEAD,
    TAIL,
    CONS,
    EQUAL,
    NONEQUAL,
    LESS,
    LESSEQ,
    GREATER,
    GREATEREQ,

    ISINT,
    ISREAL,
    ISBOOL,
    ISNULL,
    ISATOM,
    ISLIST,

    AND,
    OR,
    XOR,
    NOT,

    EOF
}
