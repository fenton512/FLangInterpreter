package ru.lapolitia.interpreter;

import java.util.HashMap;
import java.util.Map;
import ru.lapolitia.interpreter.value.Value;

/**
 * Lexically nested variable storage. Frames are mutable and closures capture them by reference,
 * which makes recursive local functions and escaping closures work.
 */
public final class Environment {
    private final Environment parent;
    private final Map<String, Value> values = new HashMap<>();

    public Environment(Environment parent) {
        this.parent = parent;
    }

    public Value lookup(String name) {
        for (Environment environment = this;
                environment != null;
                environment = environment.parent) {
            if (environment.values.containsKey(name)) {
                return environment.values.get(name);
            }
        }
        throw new RuntimeError("unknown identifier: " + name);
    }

    /** Defines or replaces a binding in this frame. */
    public void define(String name, Value value) {
        values.put(name, value);
    }

    /**
     * FLang's {@code setq} assigns a name in the current frame, creating it there when absent. This
     * shadows outer bindings instead of mutating them.
     */
    public void set(String name, Value value) {
        if (values.containsKey(name)) {
            values.put(name, value);
        } else {
            define(name, value);
        }
    }

    public boolean containsLocal(String name) {
        return values.containsKey(name);
    }

    public Environment parent() {
        return parent;
    }
}
