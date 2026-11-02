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

public interface AstVisitor {
    void visitProgram(Program expression);

    void visitIdentifier(Identifier expression);

    void visitNumberLiteral(NumberLiteral expression);

    void visitBooleanLiteral(BooleanLiteral expression);

    void visitNullLiteral(NullLiteral expression);

    void visitListExpr(ListExpr expression);

    void visitQuoteExpr(QuoteExpr expression);

    void visitSetQ(SetQ expression);

    void visitFunc(Func expression);

    void visitLambda(Lambda expression);

    void visitProg(Prog expression);

    void visitCond(Cond expression);

    void visitWhile(While expression);

    void visitReturn(Return expression);

    void visitBreak(Break expression);
}
