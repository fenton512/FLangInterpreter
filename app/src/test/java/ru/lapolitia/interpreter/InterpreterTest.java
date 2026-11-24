package ru.lapolitia.interpreter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import ru.lapolitia.ast.Program;
import ru.lapolitia.parser.Parser;

class InterpreterTest {
    private static Program parse(String source) throws IOException {
        Parser parser = new Parser(source);
        Program program = parser.parse();
        assertFalse(parser.hasErrors(), () -> "parse failed for: " + source);
        return program;
    }

    private static String evalText(String source) throws IOException {
        return ValuePrinter.format(new Interpreter().evaluate(parse(source)));
    }

    private static List<String> evalAll(String source) throws IOException {
        return new Interpreter()
                .evaluateAll(parse(source)).stream().map(ValuePrinter::format).toList();
    }

    @Test
    void evaluatesArithmeticAndPreservesNumberKinds() throws IOException {
        assertEquals("3", evalText("(plus 1 2)"));
        assertEquals("3.5", evalText("(plus 1 2.5)"));
        assertEquals("3", evalText("(divide 7 2)"));
        assertEquals("3.5", evalText("(divide 7 2.0)"));
        assertEquals("6", evalText("(minus 10 (divide 8 2))"));
        assertEquals("-2", evalText("(plus -5 3)"));

        assertEquals("true", evalText("(isint 1)"));
        assertEquals("false", evalText("(isint 1.0)"));
        assertEquals("true", evalText("(isreal 1.0)"));
        assertEquals("true", evalText("(isint (plus 1 2))"));
        assertEquals("true", evalText("(isreal (divide 7.0 2))"));
        assertEquals("3", evalText("(plus 1.0 2.0)"));
        assertEquals("true", evalText("(isreal (plus 1.0 2.0))"));
    }

    @Test
    void evaluatesListBuiltins() throws IOException {
        assertEquals("1", evalText("(head (cons 1 null))"));
        assertEquals("1", evalText("(first (1 2))"));
        assertEquals("(2 3)", evalText("(tail (1 2 3))"));
        assertEquals("(1 2 3)", evalText("(cons 1 (2 3))"));
        assertEquals("(1)", evalText("(cons 1 null)"));
        assertEquals("null", evalText("(tail (1))"));
        assertEquals("(1 2 3)", evalText("(1 2 3)"));
    }

    @Test
    void distinguishesApplicationsFromSequences() throws IOException {
        assertEquals("8", evalText("((lambda (a) (plus a a)) 4)"));
        assertEquals("3", evalText("((lambda () (plus 1 2)))"));
        assertEquals("(1 2)", evalText("((setq x 1) (plus x 1))"));
        assertEquals("(5 6)", evalText("(func f () ((setq x 5) (plus x 1))) (f)"));
    }

    @Test
    void closuresCaptureTheirLexicalEnvironment() throws IOException {
        String source =
                """
                (func makeIncrement (additor)
                    (return (lambda (x) (plus x additor))))
                (setq increment5 (makeIncrement 5))
                (increment5 10)
                """;
        assertEquals("15", evalText(source));
    }

    @Test
    void recursiveLocalClosureWorks() throws IOException {
        String source =
                """
                (func reverse (lst)
                    ((setq helper
                        (lambda (lst cur)
                            (cond
                                (isnull lst) cur
                                (helper (tail lst) (cons (head lst) cur)))))
                    (return (helper lst null))))
                (reverse (1 2 3 4 5))
                """;
        assertEquals("(5 4 3 2 1)", evalText(source));
    }

    @Test
    void setqShadowsOuterBindings() throws IOException {
        String source =
                """
                (setq g 10)
                (func f (g) ((setq g 5) (plus g 1)))
                (f 99)
                (plus g 0)
                """;
        assertEquals(List.of("10", "<closure>", "(5 6)", "10"), evalAll(source));
    }

    @Test
    void progReturnsNullWithoutReturnAndKeepsItsOwnFrame() throws IOException {
        String source =
                """
                (setq x 1)
                (prog (x)
                    (setq x 2)
                    x)
                (plus x 0)
                """;
        assertEquals(List.of("1", "null", "1"), evalAll(source));

        String withReturn =
                """
                (setq x 1)
                (func f ()
                    (prog (x)
                        (setq x 2)
                        (return x)))
                (f)
                (plus x 0)
                """;
        assertEquals(List.of("1", "<closure>", "2", "1"), evalAll(withReturn));
    }

    @Test
    void progInitializesLocalsFromTheOuterScope() throws IOException {
        String source =
                """
                (setq outer 5)
                (func f (outer) (prog (outer) (return outer)))
                (f 9)
                (plus outer 0)
                """;
        assertEquals(List.of("5", "<closure>", "9", "5"), evalAll(source));

        // return from a prog is a value of that prog, even at the top level.
        assertEquals("7", evalText("(prog () (return 7))"));
    }

    @Test
    void whileLoopAndBreakWork() throws IOException {
        String source =
                """
                (setq lst null)
                (setq i 9)
                (while true
                    ((cond (equal i 5)
                    (break))
                    (setq lst (cons i lst))
                    (setq i (minus i 1))))
                lst
                """;
        assertEquals("(6 7 8 9)", evalText(source));
    }

    @Test
    void condAndReturnWork() throws IOException {
        assertEquals("10", evalText("(cond true 10 20)"));
        assertEquals("20", evalText("(cond false 10 20)"));
        assertEquals("null", evalText("(cond false 10)"));
        assertEquals("5", evalText("(func f (x) ((return (plus x 1)) 0)) (f 4)"));

        // return belongs to the nearest lambda, not the enclosing function.
        assertEquals("(1 2)", evalText("(func outer () (((lambda () (return 1))) 2)) (outer)"));
    }

    @Test
    void quoteAndEvalWork() throws IOException {
        assertEquals("x", evalText("'x"));
        assertEquals("(plus 2 3)", evalText("'(plus 2 3)"));
        assertEquals("5", evalText("(eval '(plus 2 3))"));
        assertEquals(
                List.of("2", "2", "2", "(1 2)", "5"),
                evalAll("(setq x 2) (eval 'x) (eval 2) (eval (1 2)) (eval '(plus 2 3))"));
        assertEquals("1", evalText("(eval '(setq x 1))"));
    }

    @Test
    void evaluatesEqualityAndPredicates() throws IOException {
        assertEquals("true", evalText("(equal 1 1.0)"));
        assertEquals("true", evalText("(equal (1 2) (1 2))"));
        assertEquals("false", evalText("(equal (1 2) (1 2 3))"));
        assertEquals("true", evalText("(equal null null)"));
        assertEquals("false", evalText("(equal null (1))"));

        assertEquals("true", evalText("(isatom 1)"));
        assertEquals("false", evalText("(isatom null)"));
        assertEquals("false", evalText("(isatom (1))"));
        assertEquals("true", evalText("(islist (1))"));
        assertEquals("false", evalText("(islist null)"));
        assertEquals("true", evalText("(isbool true)"));
        assertEquals("true", evalText("(isnull null)"));
    }

    @Test
    void evaluatesLogicalOperatorsAndBooleanComparisons() throws IOException {
        assertEquals("true", evalText("(and true (not false))"));
        assertEquals("true", evalText("(or false true)"));
        assertEquals("true", evalText("(xor false true)"));
        assertEquals("false", evalText("(not true)"));
        assertEquals("true", evalText("(less false true)"));
        assertEquals("true", evalText("(greatereq true false)"));
    }

    @Test
    void reportsRuntimeErrors() {
        assertThrows(RuntimeError.class, () -> evalText("(divide 1 0)"));
        assertThrows(RuntimeError.class, () -> evalText("(divide 1.0 0.0)"));
        assertThrows(RuntimeError.class, () -> evalText("(head null)"));
        assertThrows(RuntimeError.class, () -> evalText("(tail 5)"));
        assertThrows(RuntimeError.class, () -> evalText("(plus true 1)"));
        assertThrows(RuntimeError.class, () -> evalText("(setq f 1) (f 2)"));
        assertThrows(RuntimeError.class, () -> evalText("(return 1)"));
        assertThrows(RuntimeError.class, () -> evalText("(prog (missing) null)"));
        assertThrows(RuntimeError.class, () -> evalText("(break)"));
        assertThrows(RuntimeError.class, () -> evalText("((lambda (a b) a) 1)"));
        assertThrows(RuntimeError.class, () -> evalText("(eval (cons 'plus (cons 1 null)))"));
    }
}
