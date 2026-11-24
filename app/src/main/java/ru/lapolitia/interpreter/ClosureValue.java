package ru.lapolitia.interpreter;

import java.util.List;
import java.util.Objects;
import ru.lapolitia.ast.Expression;

public record ClosureValue(List<String> parameters, Expression body, Environment environment)
        implements Value, Callable {
    public ClosureValue {
        parameters = List.copyOf(parameters);
        Objects.requireNonNull(body);
        Objects.requireNonNull(environment);
    }

    @Override
    public String name() {
        return "<closure>";
    }
}
