package ru.lapolitia.semantic;

import ru.lapolitia.ast.BooleanLiteral;
import ru.lapolitia.ast.Break;
import ru.lapolitia.ast.Cond;
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

public abstract class AbstractAstVisitor implements AstVisitor {
    @Override
    public void visitProgram(Program expression) {
        expression.expressions().forEach(child -> child.accept(this));
    }

    @Override
    public void visitIdentifier(Identifier expression) {}

    @Override
    public void visitNumberLiteral(NumberLiteral expression) {}

    @Override
    public void visitBooleanLiteral(BooleanLiteral expression) {}

    @Override
    public void visitNullLiteral(NullLiteral expression) {}

    @Override
    public void visitListExpr(ListExpr expression) {
        expression.elements().forEach(child -> child.accept(this));
    }

    @Override
    public void visitQuoteExpr(QuoteExpr expression) {}

    @Override
    public void visitSetQ(SetQ expression) {
        expression.name().accept(this);
        expression.value().accept(this);
    }

    @Override
    public void visitFunc(Func expression) {
        expression.name().accept(this);
        expression.parameters().accept(this);
        expression.body().accept(this);
    }

    @Override
    public void visitLambda(Lambda expression) {
        expression.parameters().accept(this);
        expression.body().accept(this);
    }

    @Override
    public void visitProg(Prog expression) {
        expression.locals().accept(this);
        expression.body().accept(this);
    }

    @Override
    public void visitCond(Cond expression) {
        expression.condition().accept(this);
        expression.consequent().accept(this);
        expression.alternative().ifPresent(child -> child.accept(this));
    }

    @Override
    public void visitWhile(While expression) {
        expression.condition().accept(this);
        expression.body().accept(this);
    }

    @Override
    public void visitReturn(Return expression) {
        expression.value().accept(this);
    }

    @Override
    public void visitBreak(Break expression) {}
}
