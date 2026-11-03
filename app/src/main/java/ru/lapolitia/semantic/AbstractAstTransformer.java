package ru.lapolitia.semantic;

import java.util.Optional;
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

public abstract class AbstractAstTransformer implements AstTransformer {
    @Override
    public Expression transformProgram(Program expression) {
        return new Program(expression.expressions().stream().map(this::transform).toList());
    }

    @Override
    public Expression transformIdentifier(Identifier expression) {
        return expression;
    }

    @Override
    public Expression transformNumberLiteral(NumberLiteral expression) {
        return expression;
    }

    @Override
    public Expression transformBooleanLiteral(BooleanLiteral expression) {
        return expression;
    }

    @Override
    public Expression transformNullLiteral(NullLiteral expression) {
        return expression;
    }

    @Override
    public Expression transformListExpr(ListExpr expression) {
        return new ListExpr(expression.elements().stream().map(this::transform).toList());
    }

    @Override
    public Expression transformQuoteExpr(QuoteExpr expression) {
        return expression;
    }

    @Override
    public Expression transformSetQ(SetQ expression) {
        return new SetQ((Identifier) transform(expression.name()), transform(expression.value()));
    }

    @Override
    public Expression transformFunc(Func expression) {
        return new Func(
                (Identifier) transform(expression.name()),
                (ListExpr) transform(expression.parameters()),
                transform(expression.body()));
    }

    @Override
    public Expression transformLambda(Lambda expression) {
        return new Lambda(
                (ListExpr) transform(expression.parameters()), transform(expression.body()));
    }

    @Override
    public Expression transformProg(Prog expression) {
        return new Prog(
                (ListExpr) transform(expression.locals()), (ListExpr) transform(expression.body()));
    }

    @Override
    public Expression transformCond(Cond expression) {
        Optional<Expression> alternative = expression.alternative().map(this::transform);
        return new Cond(
                transform(expression.condition()), transform(expression.consequent()), alternative);
    }

    @Override
    public Expression transformWhile(While expression) {
        return new While(transform(expression.condition()), transform(expression.body()));
    }

    @Override
    public Expression transformReturn(Return expression) {
        return new Return(transform(expression.value()));
    }

    @Override
    public Expression transformBreak(Break expression) {
        return expression;
    }

    protected final Expression transform(Expression expression) {
        return expression.accept(this);
    }
}
