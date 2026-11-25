package ru.lapolitia.interpreter.builtin;

import java.util.ArrayList;
import java.util.List;
import ru.lapolitia.interpreter.Environment;
import ru.lapolitia.interpreter.RuntimeError;
import ru.lapolitia.interpreter.value.BoolValue;
import ru.lapolitia.interpreter.value.BuiltinValue;
import ru.lapolitia.interpreter.value.IntValue;
import ru.lapolitia.interpreter.value.ListValue;
import ru.lapolitia.interpreter.value.NullValue;
import ru.lapolitia.interpreter.value.RealValue;
import ru.lapolitia.interpreter.value.Value;
import ru.lapolitia.interpreter.value.Values;

/** Registers the predefined functions in the global environment. */
public final class Builtins {
    private Builtins() {}

    @FunctionalInterface
    public interface CodeEvaluator {
        Value evaluate(Value value, Environment environment);
    }

    public static void install(Environment environment, CodeEvaluator codeEvaluator) {
        define(
                environment,
                "plus",
                2,
                (arguments, caller) ->
                        Values.arithmetic("plus", arguments.get(0), arguments.get(1)));
        define(
                environment,
                "minus",
                2,
                (arguments, caller) ->
                        Values.arithmetic("minus", arguments.get(0), arguments.get(1)));
        define(
                environment,
                "times",
                2,
                (arguments, caller) ->
                        Values.arithmetic("times", arguments.get(0), arguments.get(1)));
        define(
                environment,
                "divide",
                2,
                (arguments, caller) ->
                        Values.arithmetic("divide", arguments.get(0), arguments.get(1)));

        define(environment, "head", 1, (arguments, caller) -> head(arguments.get(0)));
        define(environment, "first", 1, (arguments, caller) -> head(arguments.get(0)));
        define(environment, "tail", 1, (arguments, caller) -> tail(arguments.get(0)));
        define(
                environment,
                "cons",
                2,
                (arguments, caller) -> cons(arguments.get(0), arguments.get(1)));

        define(
                environment,
                "equal",
                2,
                (arguments, caller) ->
                        Values.bool(Values.equal(arguments.get(0), arguments.get(1))));
        define(
                environment,
                "nonequal",
                2,
                (arguments, caller) ->
                        Values.bool(!Values.equal(arguments.get(0), arguments.get(1))));

        defineComparison(environment, "less", comparison -> comparison < 0);
        defineComparison(environment, "lesseq", comparison -> comparison <= 0);
        defineComparison(environment, "greater", comparison -> comparison > 0);
        defineComparison(environment, "greatereq", comparison -> comparison >= 0);

        define(
                environment,
                "isint",
                1,
                (arguments, caller) -> Values.bool(arguments.get(0) instanceof IntValue));
        define(
                environment,
                "isreal",
                1,
                (arguments, caller) -> Values.bool(arguments.get(0) instanceof RealValue));
        define(
                environment,
                "isbool",
                1,
                (arguments, caller) -> Values.bool(arguments.get(0) instanceof BoolValue));
        define(
                environment,
                "isnull",
                1,
                (arguments, caller) -> Values.bool(arguments.get(0) instanceof NullValue));
        define(
                environment,
                "isatom",
                1,
                (arguments, caller) ->
                        Values.bool(
                                !(arguments.get(0) instanceof NullValue)
                                        && !(arguments.get(0) instanceof ListValue)));
        define(
                environment,
                "islist",
                1,
                (arguments, caller) -> Values.bool(arguments.get(0) instanceof ListValue));

        define(
                environment,
                "and",
                2,
                (arguments, caller) ->
                        Values.bool(
                                Values.asBool(arguments.get(0), "and")
                                        && Values.asBool(arguments.get(1), "and")));
        define(
                environment,
                "or",
                2,
                (arguments, caller) ->
                        Values.bool(
                                Values.asBool(arguments.get(0), "or")
                                        || Values.asBool(arguments.get(1), "or")));
        define(
                environment,
                "xor",
                2,
                (arguments, caller) ->
                        Values.bool(
                                Values.asBool(arguments.get(0), "xor")
                                        ^ Values.asBool(arguments.get(1), "xor")));
        define(
                environment,
                "not",
                1,
                (arguments, caller) -> Values.bool(!Values.asBool(arguments.get(0), "not")));

        define(
                environment,
                "eval",
                1,
                (arguments, caller) -> codeEvaluator.evaluate(arguments.get(0), caller));
    }

    private static void defineComparison(
            Environment environment, String name, ComparisonPredicate predicate) {
        define(
                environment,
                name,
                2,
                (arguments, caller) -> {
                    int comparison = Values.compare(arguments.get(0), arguments.get(1), name);
                    return Values.bool(predicate.test(comparison));
                });
    }

    private static Value head(Value value) {
        if (value instanceof ListValue list) {
            return list.elements().get(0);
        }
        throw new RuntimeError("head: expected a non-empty list, got " + Values.typeName(value));
    }

    private static Value tail(Value value) {
        if (value instanceof ListValue list) {
            return Values.list(list.elements().subList(1, list.elements().size()));
        }
        throw new RuntimeError("tail: expected a non-empty list, got " + Values.typeName(value));
    }

    private static Value cons(Value element, Value rest) {
        List<Value> elements = new ArrayList<>();
        elements.add(element);
        if (rest instanceof ListValue list) {
            elements.addAll(list.elements());
        } else if (!(rest instanceof NullValue)) {
            throw new RuntimeError(
                    "cons: expected a list as second argument, got " + Values.typeName(rest));
        }
        return new ListValue(elements);
    }

    private static void define(
            Environment environment, String name, int arity, BuiltinFunction function) {
        environment.define(name, new BuiltinValue(name, arity, function));
    }

    @FunctionalInterface
    private interface ComparisonPredicate {
        boolean test(int comparison);
    }
}
