package ru.lapolitia.semantic;

import java.util.ArrayList;
import java.util.List;

import ru.lapolitia.ast.Program;

public final class SemanticAnalyzer {
    public List<SemanticError> analyze(Program program) {
        List<SemanticError> errors = new ArrayList<>();
        errors.addAll(new ContextVisitor().analyze(program));
        errors.addAll(new DeclarationVisitor().analyze(program));
        return List.copyOf(errors);
    }
}
