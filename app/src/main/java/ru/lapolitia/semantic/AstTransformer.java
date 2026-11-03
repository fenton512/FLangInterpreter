package ru.lapolitia.semantic;

import ru.lapolitia.ast.BooleanLiteral;
import ru.lapolitia.ast.Break;
import ru.lapolitia.ast.Cond;
import ru.lapolitia.ast.Expression;
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

public interface AstTransformer {
    Expression transformProgram(Program expression);

    Expression transformIdentifier(Identifier expression);

    Expression transformNumberLiteral(NumberLiteral expression);

    Expression transformBooleanLiteral(BooleanLiteral expression);

    Expression transformNullLiteral(NullLiteral expression);

    Expression transformListExpr(ListExpr expression);

    Expression transformQuoteExpr(QuoteExpr expression);

    Expression transformSetQ(SetQ expression);

    Expression transformFunc(Func expression);

    Expression transformLambda(Lambda expression);

    Expression transformProg(Prog expression);

    Expression transformCond(Cond expression);

    Expression transformWhile(While expression);

    Expression transformReturn(Return expression);

    Expression transformBreak(Break expression);
}
