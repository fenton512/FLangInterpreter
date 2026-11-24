package ru.lapolitia.interpreter;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import ru.lapolitia.ast.Program;
import ru.lapolitia.parser.Parser;
import ru.lapolitia.semantic.ExpressionSimplifier;
import ru.lapolitia.semantic.UnusedVariableRemover;

class OptimizerEquivalenceTest {
    private static Program parse(String source) throws IOException {
        Parser parser = new Parser(source);
        Program program = parser.parse();
        assertFalse(parser.hasErrors(), () -> "parse failed for: " + source);
        return program;
    }

    private static Program optimize(Program program) {
        Program simplified = (Program) program.accept(new ExpressionSimplifier());
        return (Program) simplified.accept(new UnusedVariableRemover());
    }

    private static String evaluate(Program program) {
        return ValuePrinter.format(new Interpreter().evaluate(program));
    }

    @Test
    void optimizerPreservesProgramResults() throws IOException {
        String[] sources = {
            "(plus 5 (times 2 3))",
            "(setq total (minus 10 (divide 8 2))) total",
            "(less 3 5)",
            "(and true (not false))",
            "(equal null null)",
            "(func calculate (value) ((setq result (plus 2 3)) (plus result value)))"
                    + " (calculate 4)",
            "(func f (x) (divide 5 x)) (plus (f 2) 1)",
            "(setq x 1) (func f () (prog (x) ((setq x 2) (return x)))) (f)",
        };

        for (String source : sources) {
            Program original = parse(source);
            Program optimized = optimize(original);
            assertEquals(evaluate(original), evaluate(optimized), source);
        }
    }

    @Test
    void optimizerKeepsBindingsReachableThroughQuoteAndEval() throws IOException {
        String source = "(setq x 1) (eval 'x)";
        Program original = parse(source);
        Program optimized = optimize(original);

        assertDoesNotThrow(() -> evaluate(original));
        assertDoesNotThrow(() -> evaluate(optimized));
        assertEquals("1", evaluate(original));
        assertEquals("1", evaluate(optimized));
    }

    @Test
    void optimizerDoesNotTreatLambdaApplicationAsSequence() throws IOException {
        String source = "((lambda (a b) a) (setq y 1) 2)";
        Program original = parse(source);
        Program optimized = optimize(original);

        assertEquals("1", evaluate(original));
        assertEquals("1", evaluate(optimized));
    }

    @Test
    void optimizerDoesNotTurnSequenceIntoCallWhenRemovingUnusedSetq() throws IOException {
        String source =
                """
                (setq dead 0)
                (setq live 2)
                (prog (dead live)
                    ((setq dead 1)
                     live))
                """;
        Program original = parse(source);
        Program optimized = optimize(original);

        assertEquals("(1 2)", evaluate(original));
        assertEquals(evaluate(original), evaluate(optimized));
    }

    @Test
    void optimizerPreservesSequenceValueWhenRemovingMiddleUnusedSetq() throws IOException {
        String source =
                """
                (setq dead 0)
                (setq live 2)
                (prog (dead live)
                    ((plus live 0)
                     (setq dead 1)
                     live))
                """;
        Program original = parse(source);
        Program optimized = optimize(original);

        assertEquals("(2 1 2)", evaluate(original));
        assertEquals(evaluate(original), evaluate(optimized));
    }
}
