package ru.lapolitia.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import ru.lapolitia.ast.AstPrinter;
import ru.lapolitia.ast.Program;
import ru.lapolitia.interpreter.Interpreter;
import ru.lapolitia.interpreter.RuntimeError;
import ru.lapolitia.interpreter.Value;
import ru.lapolitia.interpreter.ValuePrinter;
import ru.lapolitia.parser.Parser;
import ru.lapolitia.semantic.ExpressionSimplifier;
import ru.lapolitia.semantic.SemanticAnalyzer;
import ru.lapolitia.semantic.SemanticError;
import ru.lapolitia.semantic.UnusedVariableRemover;

public class Flang {

    private static boolean hasError = false;

    public static void main(String[] args) throws IOException {
        Interpreter interpreter = new Interpreter();
        boolean verbose = false;
        String path = null;
        for (String arg : args) {
            if (arg.equals("-v") || arg.equals("--verbose")) {
                verbose = true;
            } else if (path == null && !arg.startsWith("-")) {
                path = arg;
            } else {
                System.out.println("Usage: ./run.sh [-v] [path]");
                System.exit(64);
            }
        }

        if (path != null) {
            runFile(path, interpreter, verbose);
        } else {
            runPrompt(interpreter, verbose);
        }
    }

    private static void runFile(String path, Interpreter interpreter, boolean verbose)
            throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()), interpreter, true, verbose);
        if (hasError) System.exit(2);
    }

    private static void runPrompt(Interpreter interpreter, boolean verbose) throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        while (true) {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) break;
            run(line, interpreter, false, verbose);
            // to prevent killing the user session
            hasError = false;
        }
    }

    private static void run(
            String sourceCode, Interpreter interpreter, boolean checkAndOptimize, boolean verbose) {
        Parser parser = new Parser(sourceCode);
        try {
            var program = parser.parse();
            if (parser.hasErrors()) {
                if (verbose) {
                    System.out.println(AstPrinter.print(program));
                }
                hasError = true;
                return;
            }

            if (!checkAndOptimize) {
                // Prompt mode is a persistent REPL, so static checks and optimizations are
                // intentionally skipped: definitions from earlier lines are not in this program.
                if (verbose) {
                    System.out.println(AstPrinter.print(program));
                }
                reportValues(interpreter.evaluateAll(program));
                return;
            }

            if (verbose) {
                System.out.println(AstPrinter.print(program));
            }
            var semanticErrors = new SemanticAnalyzer().analyze(program);
            if (!semanticErrors.isEmpty()) {
                reportSemanticErrors(semanticErrors);
                return;
            }

            Program transformedProgram = (Program) program.accept(new ExpressionSimplifier());
            transformedProgram = (Program) transformedProgram.accept(new UnusedVariableRemover());
            if (verbose) {
                System.out.println(new String(new char[40]).replace('\0', '*'));
                System.out.println(AstPrinter.print(transformedProgram));
            }
            semanticErrors = new SemanticAnalyzer().analyze(transformedProgram);
            if (!semanticErrors.isEmpty()) {
                reportSemanticErrors(semanticErrors);
                return;
            }

            reportValues(interpreter.evaluateAll(transformedProgram));

        } catch (java.io.IOException e) {
            System.err.println(e.getMessage());
            hasError = true;
        } catch (RuntimeError e) {
            System.err.println("Runtime error: " + e.getMessage());
            hasError = true;
        }
    }

    private static void reportValues(List<Value> results) {
        for (Value result : results) {
            System.out.println("=> " + ValuePrinter.format(result));
        }
    }

    private static void reportSemanticErrors(List<SemanticError> semanticErrors) {
        for (SemanticError error : semanticErrors) {
            System.err.println("Semantic error: " + error.message());
            hasError = true;
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
