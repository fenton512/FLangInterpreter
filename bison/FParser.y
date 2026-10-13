
%language "Java"
%define api.parser.class {FParser}
%define api.package {ru.lapolitia.parser}
%define api.parser.public
%define parse.error detailed

%code imports {
import java.util.ArrayList;
import java.util.List;
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
import ru.lapolitia.ast.Program;
import ru.lapolitia.ast.QuoteExpr;
import ru.lapolitia.ast.Return;
import ru.lapolitia.ast.SetQ;
import ru.lapolitia.ast.While;
import ru.lapolitia.ast.Prog;
}

%code {
    private Program astRoot;

    public Program getAstRoot() {
        return astRoot;
    }

    private static ListExpr listWithHead(Identifier head, Expression... arguments) {
        List<Expression> elements = new ArrayList<>();
        elements.add(head);
        elements.addAll(List.of(arguments));
        return new ListExpr(elements);
    }

    private static ListExpr append(ListExpr list, Expression expression) {
        List<Expression> elements = new ArrayList<>(list.elements());
        elements.add(expression);
        return new ListExpr(elements);
    }

    private static Program append(Program program, Expression expression) {
        List<Expression> expressions = new ArrayList<>(program.expressions());
        expressions.add(expression);
        return new Program(expressions);
    }

}

%token QUOTE SETQ FUNC LAMBDA PROG COND WHILE RETURN BREAK EVAL
%token LEFT_PAREN RIGHT_PAREN QUOTE_SYM
%token NULL
%token <Double> NUMBER
%token <String> IDENTIFIER
%token <Boolean> TRUE FALSE
%token OP_PLUS OP_MINUS OP_TIMES OP_DIVIDE
%token HEAD TAIL CONS
%token EQUAL NONEQUAL LESS LESSEQ GREATER GREATEREQ
%token ISINT ISREAL ISBOOL ISNULL ISATOM ISLIST
%token AND OR XOR NOT

%type <Program> program
%type <Expression> element literal list
%type <QuoteExpr> quoted_element
%type <Identifier> operator_symbol arithmetic_operator list_unary_operator
%type <Identifier> comparison_operator predicate_operator logical_operator
%type <ListExpr> element_list_opt element_list parameter_list identifier_list_opt
%type <ListExpr> identifier_list element_sequence
%type <SetQ> setq_form
%type <Func> func_form
%type <Lambda> lambda_form
%type <Prog> prog_form
%type <Cond> cond_form
%type <While> while_form
%type <Return> return_form
%type <Break> break_form
%type <ListExpr> arithmetic_call list_call comparison_call predicate_call
%type <ListExpr> logical_call eval_call

%start program

%%

/* A program consists of one or more elements. */
program
    : element { astRoot = new Program(List.of($1)); $$ = astRoot; }
    | program element { astRoot = append($1, $2); $$ = astRoot; }
    ;

/* Atoms, literals, and lists are all elements. */
element
    : IDENTIFIER { $$ = new Identifier($1); }
    | literal { $$ = $1; }
    | list { $$ = $1; }
    | quoted_element { $$ = $1; }
    ;

/* Literals. The lexer may include the sign in NUMBER. */
literal
    : NUMBER { $$ = new NumberLiteral($1); }
    | TRUE { $$ = new BooleanLiteral($1); }
    | FALSE { $$ = new BooleanLiteral($1); }
    | NULL { $$ = new NullLiteral(); }
    ;

/* Quote syntax: 'element or (quote element). */
quoted_element
    : QUOTE_SYM element { $$ = new QuoteExpr($2); }
    | QUOTE_SYM operator_symbol { $$ = new QuoteExpr($2); }
    | LEFT_PAREN QUOTE element RIGHT_PAREN { $$ = new QuoteExpr($3); }
    ;

operator_symbol
    : arithmetic_operator { $$ = $1; }
    ;

arithmetic_operator
    : OP_PLUS { $$ = new Identifier("plus"); }
    | OP_MINUS { $$ = new Identifier("minus"); }
    | OP_TIMES { $$ = new Identifier("times"); }
    | OP_DIVIDE { $$ = new Identifier("divide"); }
    ;

list_unary_operator
    : HEAD { $$ = new Identifier("head"); }
    | TAIL { $$ = new Identifier("tail"); }
    ;

comparison_operator
    : EQUAL { $$ = new Identifier("equal"); }
    | NONEQUAL { $$ = new Identifier("nonequal"); }
    | LESS { $$ = new Identifier("less"); }
    | LESSEQ { $$ = new Identifier("lesseq"); }
    | GREATER { $$ = new Identifier("greater"); }
    | GREATEREQ { $$ = new Identifier("greatereq"); }
    ;

predicate_operator
    : ISINT { $$ = new Identifier("isint"); }
    | ISREAL { $$ = new Identifier("isreal"); }
    | ISBOOL { $$ = new Identifier("isbool"); }
    | ISNULL { $$ = new Identifier("isnull"); }
    | ISATOM { $$ = new Identifier("isatom"); }
    | ISLIST { $$ = new Identifier("islist"); }
    ;

logical_operator
    : AND { $$ = new Identifier("and"); }
    | OR { $$ = new Identifier("or"); }
    | XOR { $$ = new Identifier("xor"); }
    ;

/* General lists and special forms. */
list
    : LEFT_PAREN element_list_opt RIGHT_PAREN { $$ = $2; }
    | setq_form { $$ = $1; }
    | func_form { $$ = $1; }
    | lambda_form { $$ = $1; }
    | prog_form { $$ = $1; }
    | cond_form { $$ = $1; }
    | while_form { $$ = $1; }
    | return_form { $$ = $1; }
    | break_form { $$ = $1; }
    | arithmetic_call { $$ = $1; }
    | list_call { $$ = $1; }
    | comparison_call { $$ = $1; }
    | predicate_call { $$ = $1; }
    | logical_call { $$ = $1; }
    | eval_call { $$ = $1; }
    ;

/* Zero or more elements, allowing () and (func-name ...). */
element_list_opt
    : %empty { $$ = new ListExpr(List.of()); }
    | element_list { $$ = $1; }
    ;

element_list
    : element { $$ = new ListExpr(List.of($1)); }
    | element_list element { $$ = append($1, $2); }
    ;

/* Special forms with their required argument counts. */
setq_form
        : LEFT_PAREN SETQ IDENTIFIER element RIGHT_PAREN
            { $$ = new SetQ(new Identifier($3), $4); }
    ;

func_form
        : LEFT_PAREN FUNC IDENTIFIER parameter_list element RIGHT_PAREN
            { $$ = new Func(new Identifier($3), $4, $5); }
    ;

lambda_form
        : LEFT_PAREN LAMBDA parameter_list element RIGHT_PAREN
            { $$ = new Lambda($3, $4); }
    ;

prog_form
        : LEFT_PAREN PROG parameter_list element_sequence RIGHT_PAREN
            { $$ = new Prog($3, $4); }
    ;

cond_form
        : LEFT_PAREN COND element element RIGHT_PAREN
            { $$ = new Cond($3, $4, Optional.empty()); }
        | LEFT_PAREN COND element element element RIGHT_PAREN
            { $$ = new Cond($3, $4, Optional.of($5)); }
    ;

while_form
        : LEFT_PAREN WHILE element element RIGHT_PAREN
            { $$ = new While($3, $4); }
    ;

return_form
        : LEFT_PAREN RETURN element RIGHT_PAREN
            { $$ = new Return($3); }
    ;

break_form
        : LEFT_PAREN BREAK RIGHT_PAREN
            { $$ = new Break(); }
    ;

/* Parameter/local-variable list: (x y z) or (). */
parameter_list
    : LEFT_PAREN identifier_list_opt RIGHT_PAREN { $$ = $2; }
    ;

identifier_list_opt
    : %empty { $$ = new ListExpr(List.of()); }
    | identifier_list { $$ = $1; }
    ;

identifier_list
    : IDENTIFIER { $$ = new ListExpr(List.of(new Identifier($1))); }
    | identifier_list IDENTIFIER { $$ = append($1, new Identifier($2)); }
    ;

/* Non-empty sequence of elements, used by prog. */
element_sequence
    : element { $$ = new ListExpr(List.of($1)); }
    | element_sequence element { $$ = append($1, $2); }
    ;

/* Predefined function calls. */
arithmetic_call
        : LEFT_PAREN arithmetic_operator element element RIGHT_PAREN
            { $$ = listWithHead($2, $3, $4); }
    ;

list_call
        : LEFT_PAREN list_unary_operator element RIGHT_PAREN
            { $$ = listWithHead($2, $3); }
        | LEFT_PAREN CONS element element RIGHT_PAREN
            { $$ = listWithHead(new Identifier("cons"), $3, $4); }
    ;

comparison_call
        : LEFT_PAREN comparison_operator element element RIGHT_PAREN
            { $$ = listWithHead($2, $3, $4); }
    ;

predicate_call
        : LEFT_PAREN predicate_operator element RIGHT_PAREN
            { $$ = listWithHead($2, $3); }
    ;

logical_call
        : LEFT_PAREN logical_operator element element RIGHT_PAREN
            { $$ = listWithHead($2, $3, $4); }
        | LEFT_PAREN NOT element RIGHT_PAREN
            { $$ = listWithHead(new Identifier("not"), $3); }
    ;

eval_call
        : LEFT_PAREN EVAL element RIGHT_PAREN
            { $$ = listWithHead(new Identifier("eval"), $3); }
    ;

%%
