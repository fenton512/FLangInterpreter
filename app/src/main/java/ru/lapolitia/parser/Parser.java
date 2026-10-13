package ru.lapolitia.parser;

import java.io.IOException;
import ru.lapolitia.ast.Program;
import ru.lapolitia.lexer.Scanner;

public class Parser {
    private final FParser generatedParser;

    public Parser(String source) {
        generatedParser = new FParser(new Scanner(source));
    }

    public Program parse() throws IOException {
        boolean succeeded = generatedParser.parse();
        if (!succeeded || generatedParser.getNumberOfErrors() > 0) {
            throw new ParseException("Parsing failed");
        }

        Program astRoot = generatedParser.getAstRoot();
        if (astRoot == null) {
            throw new ParseException("Parser did not produce an AST");
        }
        return astRoot;
    }
}
