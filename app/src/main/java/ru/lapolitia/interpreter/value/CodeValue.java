package ru.lapolitia.interpreter.value;

import java.util.Objects;
import ru.lapolitia.ast.Expression;

/**
 * Quoted expression that cannot be represented as ordinary list data, such as {@code '(setq x 1)}.
 * {@code eval} evaluates the wrapped expression.
 */
public record CodeValue(Expression expression) implements Value {
    public CodeValue {
        Objects.requireNonNull(expression);
    }
}
