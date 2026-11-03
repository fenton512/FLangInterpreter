package ru.lapolitia.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import ru.lapolitia.ast.AstPrinter;
import ru.lapolitia.ast.Program;
import ru.lapolitia.parser.Parser;
import ru.lapolitia.semantic.ExpressionSimplifier;
import ru.lapolitia.semantic.SemanticAnalyzer;
import ru.lapolitia.semantic.SemanticError;
import ru.lapolitia.semantic.UnusedVariableRemover;

public class Flang {

    private static boolean hasError = false;

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: ./run.sh [path]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        if (hasError) System.exit(2);
    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        while (true) {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) break;
            run(line);
            // to prevent killing the user session
            hasError = false;
        }
    }

    private static void run(String sourceCode) {
        Parser parser = new Parser(sourceCode);
        try {
            var program = parser.parse();
            if (parser.hasErrors()) {
                System.out.println(AstPrinter.print(program));
                hasError = true;
                return;
            }

            System.out.println(AstPrinter.print(program));
            var semanticErrors = new SemanticAnalyzer().analyze(program);
            if (!semanticErrors.isEmpty()) {
                for (SemanticError error : semanticErrors) {
                    System.err.println("Semantic error: " + error.message());
                    hasError = true;
                }
                return;
            }

            Program transformedProgram = (Program) program.accept(new ExpressionSimplifier());
            transformedProgram = (Program) transformedProgram.accept(new UnusedVariableRemover());
            System.out.println(new String(new char[40]).replace('\0', '*'));
            System.out.println(AstPrinter.print(transformedProgram));
            semanticErrors = new SemanticAnalyzer().analyze(transformedProgram);
            if (!semanticErrors.isEmpty()) {
                for (SemanticError error : semanticErrors) {
                    System.err.println("Semantic error: " + error.message());
                    hasError = true;
                }
            }

        } catch (java.io.IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void reportError(int line, String where, String message) {
        System.err.println("Error in line " + line + ": " + where + "detail" + message);
        hasError = true;
    }

    public static void reportError(int line, String message) {
        System.err.println("Error in line " + line + " detail: " + message);
        hasError = true;
    }

    public static void reportError(String message) {
        System.err.println("Error: " + message);
        hasError = true;
    }
}
