package ru.lapolitia.semantic;

import java.util.ArrayList;
import java.util.List;
import ru.lapolitia.ast.Break;
import ru.lapolitia.ast.Expression;
import ru.lapolitia.ast.Func;
import ru.lapolitia.ast.Lambda;
import ru.lapolitia.ast.Prog;
import ru.lapolitia.ast.Program;
import ru.lapolitia.ast.Return;
import ru.lapolitia.ast.While;

public final class ContextVisitor extends AbstractAstVisitor {
    private final List<SemanticError> errors = new ArrayList<>();
    private int localContextDepth;
    private int loopDepth;

    public List<SemanticError> analyze(Program program) {
        errors.clear();
        localContextDepth = 0;
        loopDepth = 0;
        program.accept(this);
        return List.copyOf(errors);
    }

    @Override
    public void visitFunc(Func expression) {
        expression.name().accept(this);
        expression.parameters().accept(this);
        visitNewLocalContext(expression.body());
    }

    @Override
    public void visitLambda(Lambda expression) {
        expression.parameters().accept(this);
        visitNewLocalContext(expression.body());
    }

    @Override
    public void visitProg(Prog expression) {
        expression.locals().accept(this);
        visitExistingExecutionContext(expression.body());
    }

    @Override
    public void visitWhile(While expression) {
        expression.condition().accept(this);
        loopDepth++;
        expression.body().accept(this);
        loopDepth--;
    }

    @Override
    public void visitReturn(Return expression) {
        if (localContextDepth == 0) {
            errors.add(new SemanticError("return used outside a local context"));
        }
        expression.value().accept(this);
    }

    @Override
    public void visitBreak(Break expression) {
        if (loopDepth == 0) {
            errors.add(new SemanticError("break used outside a while loop"));
        }
    }

    private void visitNewLocalContext(Expression body) {
        int previousLoopDepth = loopDepth;
        localContextDepth++;
        loopDepth = 0;
        try {
            body.accept(this);
        } finally {
            loopDepth = previousLoopDepth;
            localContextDepth--;
        }
    }

    private void visitExistingExecutionContext(Expression body) {
        localContextDepth++;
        try {
            body.accept(this);
        } finally {
            localContextDepth--;
        }
    }
}
