package ru.lapolitia.parser;

import java.io.IOException;
import java.util.List;
import ru.lapolitia.ast.Program;
import ru.lapolitia.lexer.Scanner;

public class Parser {
    private final Scanner scanner;
    private final FParser generatedParser;

    public Parser(String source) {
        scanner = new Scanner(source);
        generatedParser = new FParser(scanner);
    }

    public List<String> getErrors() {
        return scanner.getErrors();
    }

    public Program parse() throws IOException {
        boolean succeeded = generatedParser.parse();
        List<String> errors = getErrors();
        if (!succeeded || generatedParser.getNumberOfErrors() > 0 || !errors.isEmpty()) {
            String message = errors.isEmpty()
                    ? "Parsing failed"
                    : String.join(System.lineSeparator(), errors);
            throw new ParseException(message);
        }

        Program astRoot = generatedParser.getAstRoot();
        if (astRoot == null) {
            throw new ParseException("Parser did not produce an AST");
        }
        return astRoot;
    }
}
