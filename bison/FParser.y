
%language "Java"
%define api.parser.class {FParser}
%define api.package {ru.lapolitia.parser}
%define api.parser.public
%define parse.error detailed

%token QUOTE SETQ FUNC LAMBDA PROG COND WHILE RETURN BREAK EVAL
%token LEFT_PAREN RIGHT_PAREN QUOTE_SYM
%token NULL NUMBER IDENTIFIER TRUE FALSE
%token OP_PLUS OP_MINUS OP_TIMES OP_DIVIDE
%token HEAD TAIL CONS
%token EQUAL NONEQUAL LESS LESSEQ GREATER GREATEREQ
%token ISINT ISREAL ISBOOL ISNULL ISATOM ISLIST
%token AND OR XOR NOT

%start program

%%

/* A program consists of one or more elements. */
program
    : element
    | program element
    ;

/* Atoms, literals, and lists are all elements. */
element
    : IDENTIFIER
    | literal
    | list
    | quoted_element
    ;

/* Literals. The lexer may include the sign in NUMBER. */
literal
    : NUMBER
    | TRUE
    | FALSE
    | NULL
    ;

/* Quote syntax: 'element or (quote element). */
quoted_element
    : QUOTE_SYM element
    | QUOTE_SYM operator_symbol
    | LEFT_PAREN QUOTE element RIGHT_PAREN
    ;

operator_symbol
    : OP_PLUS
    | OP_MINUS
    | OP_TIMES
    | OP_DIVIDE
    ;

/* General lists and special forms. */
list
    : LEFT_PAREN element_list_opt RIGHT_PAREN
    | setq_form
    | func_form
    | lambda_form
    | prog_form
    | cond_form
    | while_form
    | return_form
    | break_form
    | arithmetic_call
    | list_call
    | comparison_call
    | predicate_call
    | logical_call
    | eval_call
    ;

/* Zero or more elements, allowing () and (func-name ...). */
element_list_opt
    : %empty
    | element_list
    ;

element_list
    : element
    | element_list element
    ;

/* Special forms with their required argument counts. */
setq_form
    : LEFT_PAREN SETQ IDENTIFIER element RIGHT_PAREN
    ;

func_form
    : LEFT_PAREN FUNC IDENTIFIER parameter_list element RIGHT_PAREN
    ;

lambda_form
    : LEFT_PAREN LAMBDA parameter_list element RIGHT_PAREN
    ;

prog_form
    : LEFT_PAREN PROG parameter_list element_sequence RIGHT_PAREN
    ;

cond_form
    : LEFT_PAREN COND element element RIGHT_PAREN
    | LEFT_PAREN COND element element element RIGHT_PAREN
    ;

while_form
    : LEFT_PAREN WHILE element element RIGHT_PAREN
    ;

return_form
    : LEFT_PAREN RETURN element RIGHT_PAREN
    ;

break_form
    : LEFT_PAREN BREAK RIGHT_PAREN
    ;

/* Parameter/local-variable list: (x y z) or (). */
parameter_list
    : LEFT_PAREN identifier_list_opt RIGHT_PAREN
    ;

identifier_list_opt
    : %empty
    | identifier_list
    ;

identifier_list
    : IDENTIFIER
    | identifier_list IDENTIFIER
    ;

/* Non-empty sequence of elements, used by prog. */
element_sequence
    : element
    | element_sequence element
    ;

/* Predefined function calls. */
arithmetic_call
    : LEFT_PAREN OP_PLUS element element RIGHT_PAREN
    | LEFT_PAREN OP_MINUS element element RIGHT_PAREN
    | LEFT_PAREN OP_TIMES element element RIGHT_PAREN
    | LEFT_PAREN OP_DIVIDE element element RIGHT_PAREN
    ;

list_call
    : LEFT_PAREN HEAD element RIGHT_PAREN
    | LEFT_PAREN TAIL element RIGHT_PAREN
    | LEFT_PAREN CONS element element RIGHT_PAREN
    ;

comparison_call
    : LEFT_PAREN EQUAL element element RIGHT_PAREN
    | LEFT_PAREN NONEQUAL element element RIGHT_PAREN
    | LEFT_PAREN LESS element element RIGHT_PAREN
    | LEFT_PAREN LESSEQ element element RIGHT_PAREN
    | LEFT_PAREN GREATER element element RIGHT_PAREN
    | LEFT_PAREN GREATEREQ element element RIGHT_PAREN
    ;

predicate_call
    : LEFT_PAREN ISINT element RIGHT_PAREN
    | LEFT_PAREN ISREAL element RIGHT_PAREN
    | LEFT_PAREN ISBOOL element RIGHT_PAREN
    | LEFT_PAREN ISNULL element RIGHT_PAREN
    | LEFT_PAREN ISATOM element RIGHT_PAREN
    | LEFT_PAREN ISLIST element RIGHT_PAREN
    ;

logical_call
    : LEFT_PAREN AND element element RIGHT_PAREN
    | LEFT_PAREN OR element element RIGHT_PAREN
    | LEFT_PAREN XOR element element RIGHT_PAREN
    | LEFT_PAREN NOT element RIGHT_PAREN
    ;

eval_call
    : LEFT_PAREN EVAL element RIGHT_PAREN
    ;

%%
