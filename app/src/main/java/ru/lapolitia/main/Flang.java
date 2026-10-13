package ru.lapolitia.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import ru.lapolitia.ast.AstPrinter;
import ru.lapolitia.parser.Parser;

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
            System.out.println(AstPrinter.print(parser.parse()));
            if (parser.hasErrors()) hasError = true;
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
