package ru.lapolitia.semantic;

import java.util.List;
import ru.lapolitia.ast.Program;

public final class SemanticAnalyzer {
    public List<SemanticError> analyze(Program program) {
        return new ContextVisitor().analyze(program);
    }
}
