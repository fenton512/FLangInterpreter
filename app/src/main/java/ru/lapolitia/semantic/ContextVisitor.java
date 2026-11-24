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
    private int returnDepth;
    private int loopDepth;

    public List<SemanticError> analyze(Program program) {
        errors.clear();
        returnDepth = 0;
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
        visitExistingReturnContext(expression.body());
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
        if (returnDepth == 0) {
            errors.add(new SemanticError("return used outside a function or prog"));
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
        returnDepth++;
        loopDepth = 0;
        try {
            body.accept(this);
        } finally {
            loopDepth = previousLoopDepth;
            returnDepth--;
        }
    }

    private void visitExistingReturnContext(Expression body) {
        returnDepth++;
        try {
            body.accept(this);
        } finally {
            returnDepth--;
        }
    }
}
