package ru.lapolitia.interpreter;

import java.util.ArrayList;
import java.util.List;
import ru.lapolitia.ast.AstShapes;
import ru.lapolitia.ast.BooleanLiteral;
import ru.lapolitia.ast.Break;
import ru.lapolitia.ast.Cond;
import ru.lapolitia.ast.Expression;
import ru.lapolitia.ast.Func;
import ru.lapolitia.ast.Identifier;
import ru.lapolitia.ast.Lambda;
import ru.lapolitia.ast.ListExpr;
import ru.lapolitia.ast.NullLiteral;
import ru.lapolitia.ast.NumberLiteral;
import ru.lapolitia.ast.Prog;
import ru.lapolitia.ast.Program;
import ru.lapolitia.ast.QuoteExpr;
import ru.lapolitia.ast.Return;
import ru.lapolitia.ast.SetQ;
import ru.lapolitia.ast.While;

/** Tree-walking evaluator for the FLang AST. */
public final class Interpreter {
    private final Environment global;

    public Interpreter() {
        global = new Environment(null);
        Builtins.install(global, this::evaluateData);
    }

    public Environment globalEnvironment() {
        return global;
    }

    /** Evaluates every top-level expression and returns their values in order. */
    public List<Value> evaluateAll(Program program) {
        List<Value> values = new ArrayList<>(program.expressions().size());
        for (Expression expression : program.expressions()) {
            try {
                values.add(eval(expression, global));
            } catch (ReturnSignal signal) {
                throw new RuntimeError("return used outside a function");
            } catch (BreakSignal signal) {
                throw new RuntimeError("break used outside a while loop");
            }
        }
        return List.copyOf(values);
    }

    /** Evaluates the program and returns the value of its last expression. */
    public Value evaluate(Program program) {
        List<Value> values = evaluateAll(program);
        return values.isEmpty() ? Values.NULL : values.get(values.size() - 1);
    }

    private Value eval(Expression expression, Environment environment) {
        return switch (expression) {
            case NumberLiteral number ->
                    number.isInt()
                            ? new IntValue((long) number.value())
                            : new RealValue(number.value());
            case BooleanLiteral bool -> Values.bool(bool.value());
            case NullLiteral nullLiteral -> Values.NULL;
            case Identifier identifier -> environment.lookup(identifier.name());
            case QuoteExpr quote -> quote(quote.expression());
            case SetQ setQ -> {
                Value value = eval(setQ.value(), environment);
                environment.set(setQ.name().name(), value);
                yield value;
            }
            case Func func -> {
                ClosureValue closure =
                        new ClosureValue(
                                AstShapes.parameterNames(func.parameters()),
                                func.body(),
                                environment);
                environment.define(func.name().name(), closure);
                yield closure;
            }
            case Lambda lambda ->
                    new ClosureValue(
                            AstShapes.parameterNames(lambda.parameters()),
                            lambda.body(),
                            environment);
            case Prog prog -> evalProg(prog, environment);
            case Cond cond -> evalCond(cond, environment);
            case While whileForm -> evalWhile(whileForm, environment);
            case Return returnForm -> throw new ReturnSignal(eval(returnForm.value(), environment));
            case Break breakForm -> throw new BreakSignal();
            case ListExpr list -> evalList(list, environment);
            case Program program -> evalProgram(program, environment);
        };
    }

    private Value evalProgram(Program program, Environment environment) {
        Value result = Values.NULL;
        for (Expression expression : program.expressions()) {
            result = eval(expression, environment);
        }
        return result;
    }

    private Value evalList(ListExpr list, Environment environment) {
        List<Expression> elements = list.elements();
        if (AstShapes.isApplication(list)) {
            Value head = eval(elements.get(0), environment);
            if (!(head instanceof Callable callable)) {
                throw new RuntimeError(
                        "attempted to call a non-callable value of type " + Values.typeName(head));
            }
            List<Value> arguments = new ArrayList<>(elements.size() - 1);
            for (int index = 1; index < elements.size(); index++) {
                arguments.add(eval(elements.get(index), environment));
            }
            return apply(callable, arguments, environment);
        }

        List<Value> values = new ArrayList<>(elements.size());
        for (Expression element : elements) {
            values.add(eval(element, environment));
        }
        return Values.list(values);
    }

    private Value apply(Callable callable, List<Value> arguments, Environment caller) {
        if (callable instanceof BuiltinValue builtin) {
            if (arguments.size() != builtin.arity()) {
                throw new RuntimeError(
                        builtin.name()
                                + ": expected "
                                + builtin.arity()
                                + " argument(s), got "
                                + arguments.size());
            }
            return builtin.invoke(arguments, caller);
        }

        if (callable instanceof ClosureValue closure) {
            if (arguments.size() != closure.parameters().size()) {
                throw new RuntimeError(
                        "closure: expected "
                                + closure.parameters().size()
                                + " argument(s), got "
                                + arguments.size());
            }
            Environment frame = new Environment(closure.environment());
            for (int index = 0; index < arguments.size(); index++) {
                frame.define(closure.parameters().get(index), arguments.get(index));
            }
            try {
                return eval(closure.body(), frame);
            } catch (ReturnSignal signal) {
                return signal.value();
            }
        }

        throw new IllegalStateException("unknown callable: " + callable);
    }

    private Value evalProg(Prog prog, Environment environment) {
        Environment frame = new Environment(environment);
        for (Expression local : prog.locals().elements()) {
            if (local instanceof Identifier identifier) {
                // A prog local is initialized from the enclosing binding. The language requires
                // that binding to exist.
                try {
                    frame.define(identifier.name(), environment.lookup(identifier.name()));
                } catch (RuntimeError error) {
                    throw new RuntimeError(
                            "prog: local "
                                    + identifier.name()
                                    + " has no value in the outer scope");
                }
            }
        }

        Value result = Values.NULL;
        try {
            for (Expression expression : prog.body().elements()) {
                result = eval(expression, frame);
            }
        } catch (ReturnSignal signal) {
            return signal.value();
        }
        return result;
    }

    private Value evalCond(Cond cond, Environment environment) {
        boolean condition = Values.asBool(eval(cond.condition(), environment), "cond");
        if (condition) {
            return eval(cond.consequent(), environment);
        }
        return cond.alternative()
                .map(alternative -> eval(alternative, environment))
                .orElse(Values.NULL);
    }

    private Value evalWhile(While whileForm, Environment environment) {
        while (Values.asBool(eval(whileForm.condition(), environment), "while")) {
            try {
                eval(whileForm.body(), environment);
            } catch (BreakSignal signal) {
                break;
            }
        }
        return Values.NULL;
    }

    private Value quote(Expression expression) {
        return switch (expression) {
            case Identifier identifier -> new SymbolValue(identifier.name());
            case NumberLiteral number -> Values.number(number.value(), number.isInt());
            case BooleanLiteral bool -> Values.bool(bool.value());
            case NullLiteral nullLiteral -> Values.NULL;
            case ListExpr list -> Values.list(list.elements().stream().map(this::quote).toList());
            case Program program -> new CodeValue(program);
            case QuoteExpr quote -> new CodeValue(quote);
            case SetQ setQ -> new CodeValue(setQ);
            case Func func -> new CodeValue(func);
            case Lambda lambda -> new CodeValue(lambda);
            case Prog prog -> new CodeValue(prog);
            case Cond cond -> new CodeValue(cond);
            case While whileForm -> new CodeValue(whileForm);
            case Return returnForm -> new CodeValue(returnForm);
            case Break breakForm -> new CodeValue(breakForm);
        };
    }

    private Value evaluateData(Value value, Environment environment) {
        if (value instanceof CodeValue code) {
            return eval(code.expression(), environment);
        }
        if (value instanceof SymbolValue symbol) {
            return environment.lookup(symbol.name());
        }
        if (value instanceof ListValue list) {
            return eval(toExpression(list), environment);
        }
        return value;
    }

    private Expression toExpression(Value value) {
        if (value instanceof IntValue integer) {
            return new NumberLiteral(integer.value(), true);
        }
        if (value instanceof RealValue real) {
            return new NumberLiteral(real.value(), false);
        }
        if (value instanceof BoolValue bool) {
            return new BooleanLiteral(bool.value());
        }
        if (value instanceof NullValue) {
            return new NullLiteral();
        }
        if (value instanceof SymbolValue symbol) {
            return new Identifier(symbol.name());
        }
        if (value instanceof CodeValue code) {
            return code.expression();
        }
        if (value instanceof ListValue list) {
            return new ListExpr(list.elements().stream().map(this::toExpression).toList());
        }
        throw new RuntimeError("cannot evaluate " + Values.typeName(value) + " as code");
    }
}
