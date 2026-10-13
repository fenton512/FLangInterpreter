package ru.lapolitia.parser;

import java.io.IOException;
import java.util.List;
import ru.lapolitia.ast.Program;
import ru.lapolitia.lexer.Scanner;

public class Parser {
    private final Scanner scanner;
    private final FParser generatedParser;
    private boolean parseFailed;

    public Parser(String source) {
        scanner = new Scanner(source);
        generatedParser = new FParser(scanner);
    }

    public List<String> getErrors() {
        return scanner.getErrors();
    }

    public boolean hasErrors() {
        return parseFailed;
    }

    public Program parse() throws IOException {
        boolean succeeded = generatedParser.parse();
        List<String> errors = getErrors();
        parseFailed = !succeeded || generatedParser.getNumberOfErrors() > 0 || !errors.isEmpty();
        if (parseFailed) {
            if (errors.isEmpty()) {
                System.err.println("Parsing failed");
            } else {
                errors.forEach(System.err::println);
            }
        }

        Program astRoot = generatedParser.getAstRoot();
        if (astRoot == null) {
            return new Program(List.of());
        }
        return astRoot;
    }
}
