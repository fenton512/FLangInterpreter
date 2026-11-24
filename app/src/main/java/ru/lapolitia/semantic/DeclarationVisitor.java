package ru.lapolitia.semantic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import ru.lapolitia.ast.AstShapes;
import ru.lapolitia.ast.Expression;
import ru.lapolitia.ast.Func;
import ru.lapolitia.ast.Identifier;
import ru.lapolitia.ast.Lambda;
import ru.lapolitia.ast.ListExpr;
import ru.lapolitia.ast.Prog;
import ru.lapolitia.ast.Program;
import ru.lapolitia.ast.QuoteExpr;
import ru.lapolitia.ast.SetQ;

public final class DeclarationVisitor extends AbstractAstVisitor {
    private static final Set<String> BUILT_IN_NAMES =
            Set.of(
                    "quote",
                    "setq",
                    "func",
                    "lambda",
                    "prog",
                    "cond",
                    "while",
                    "return",
                    "break",
                    "eval",
                    "plus",
                    "minus",
                    "times",
                    "divide",
                    "head",
                    "first",
                    "tail",
                    "cons",
                    "equal",
                    "nonequal",
                    "less",
                    "lesseq",
                    "greater",
                    "greatereq",
                    "isint",
                    "isreal",
                    "isbool",
                    "isnull",
                    "isatom",
                    "islist",
                    "and",
                    "or",
                    "xor",
                    "not",
                    "true",
                    "false",
                    "null");

    private final List<SemanticError> errors = new ArrayList<>();
    private final Deque<Map<String, SymbolKind>> scopes = new ArrayDeque<>();

    public List<SemanticError> analyze(Program program) {
        errors.clear();
        scopes.clear();
        scopes.push(new HashMap<>());
        program.accept(this);
        return List.copyOf(errors);
    }

    @Override
    public void visitIdentifier(Identifier expression) {
        if (find(expression.name()) == null) {
            report("unknown identifier: " + expression.name());
        }
    }

    @Override
    public void visitListExpr(ListExpr expression) {
        List<Expression> elements = expression.elements();
        if (elements.isEmpty()) {
            return;
        }

        Expression head = elements.get(0);
        if (head instanceof Identifier identifier) {
            if (!BUILT_IN_NAMES.contains(identifier.name())) {
                requireDeclared(identifier.name());
            }
            elements.subList(1, elements.size()).forEach(child -> child.accept(this));
            return;
        }

        elements.forEach(child -> child.accept(this));
    }

    @Override
    public void visitSetQ(SetQ expression) {
        String name = expression.name().name();
        SymbolKind current = currentScope().get(name);
        if (current == SymbolKind.FUNCTION || isBuiltIn(name)) {
            report("cannot assign to reserved name: " + name);
        } else if (current == null) {
            currentScope().put(name, SymbolKind.VARIABLE);
        }
        expression.value().accept(this);
    }

    @Override
    public void visitFunc(Func expression) {
        String name = expression.name().name();
        declare(name, SymbolKind.FUNCTION);

        scopes.push(new HashMap<>());
        try {
            declareParameters(expression.parameters());
            visitFunctionBody(expression.body());
        } finally {
            scopes.pop();
        }
    }

    @Override
    public void visitLambda(Lambda expression) {
        scopes.push(new HashMap<>());
        try {
            declareParameters(expression.parameters());
            visitFunctionBody(expression.body());
        } finally {
            scopes.pop();
        }
    }

    @Override
    public void visitProg(Prog expression) {
        scopes.push(new HashMap<>());
        try {
            declareParameters(expression.locals());
            expression.body().elements().forEach(child -> child.accept(this));
        } finally {
            scopes.pop();
        }
    }

    @Override
    public void visitQuoteExpr(QuoteExpr expression) {}

    private void visitFunctionBody(Expression body) {
        if (body instanceof ListExpr list && AstShapes.isSequence(list)) {
            list.elements().forEach(child -> child.accept(this));
        } else {
            body.accept(this);
        }
    }

    private void declareParameters(ListExpr parameters) {
        parameters
                .elements()
                .forEach(
                        parameter -> declare(((Identifier) parameter).name(), SymbolKind.VARIABLE));
    }

    private void declare(String name, SymbolKind kind) {
        if (isBuiltIn(name)) {
            report("cannot declare reserved name: " + name);
        } else if (currentScope().containsKey(name)) {
            report("duplicate declaration: " + name);
        } else {
            currentScope().put(name, kind);
        }
    }

    private void requireDeclared(String name) {
        if (find(name) == null) {
            report("unknown identifier: " + name);
        }
    }

    private SymbolKind find(String name) {
        for (Map<String, SymbolKind> scope : scopes) {
            SymbolKind symbol = scope.get(name);
            if (symbol != null) {
                return symbol;
            }
        }
        return null;
    }

    private Map<String, SymbolKind> currentScope() {
        return scopes.peek();
    }

    private boolean isBuiltIn(String name) {
        return BUILT_IN_NAMES.contains(name);
    }

    private void report(String message) {
        errors.add(new SemanticError(message));
    }

    private enum SymbolKind {
        VARIABLE,
        FUNCTION
    }
}
