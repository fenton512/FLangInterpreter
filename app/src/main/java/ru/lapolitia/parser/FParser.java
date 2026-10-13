/* A Bison parser, made by GNU Bison 3.8.2.  */

/* Skeleton implementation for Bison LALR(1) parsers in Java

Copyright (C) 2007-2015, 2018-2021 Free Software Foundation, Inc.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
part or all of the Bison parser skeleton and distribute that work
under terms of your choice, so long as that work isn't itself a
parser generator using the skeleton or a modified version thereof
as a parser skeleton.  Alternatively, if you modify or redistribute
the parser skeleton itself, you may (at your option) remove this
special exception, which will cause the skeleton and the resulting
Bison output files to be licensed under the GNU General Public
License without this special exception.

This special exception was added by the Free Software Foundation in
version 2.2 of Bison.  */

/* DO NOT RELY ON FEATURES THAT ARE NOT DOCUMENTED in the manual,
especially those whose name start with YY_ or yy_.  They are
private implementation details that can be changed or removed.  */

package ru.lapolitia.parser;

import java.text.MessageFormat;

/**
 * A Bison parser, automatically generated from <tt>bison/FParser.y</tt>.
 *
 * @author LALR (1) parser skeleton written by Paolo Bonzini.
 */
public class FParser {
    /** Version number for the Bison executable that generated this parser.  */
    public static final String bisonVersion = "3.8.2";

    /** Name of the skeleton that generated this parser.  */
    public static final String bisonSkeleton = "lalr1.java";

    /**
     * True if verbose error messages are enabled.
     */
    private boolean yyErrorVerbose = true;

    /**
     * Whether verbose error messages are enabled.
     */
    public final boolean getErrorVerbose() {
        return yyErrorVerbose;
    }

    /**
     * Set the verbosity of error messages.
     * @param verbose True to request verbose error messages.
     */
    public final void setErrorVerbose(boolean verbose) {
        yyErrorVerbose = verbose;
    }

    public enum SymbolKind {
        S_YYEOF(0), /* "end of file"  */
        S_YYerror(1), /* error  */
        S_YYUNDEF(2), /* "invalid token"  */
        S_QUOTE(3), /* QUOTE  */
        S_SETQ(4), /* SETQ  */
        S_FUNC(5), /* FUNC  */
        S_LAMBDA(6), /* LAMBDA  */
        S_PROG(7), /* PROG  */
        S_COND(8), /* COND  */
        S_WHILE(9), /* WHILE  */
        S_RETURN(10), /* RETURN  */
        S_BREAK(11), /* BREAK  */
        S_EVAL(12), /* EVAL  */
        S_LEFT_PAREN(13), /* LEFT_PAREN  */
        S_RIGHT_PAREN(14), /* RIGHT_PAREN  */
        S_QUOTE_SYM(15), /* QUOTE_SYM  */
        S_NULL(16), /* NULL  */
        S_NUMBER(17), /* NUMBER  */
        S_IDENTIFIER(18), /* IDENTIFIER  */
        S_TRUE(19), /* TRUE  */
        S_FALSE(20), /* FALSE  */
        S_OP_PLUS(21), /* OP_PLUS  */
        S_OP_MINUS(22), /* OP_MINUS  */
        S_OP_TIMES(23), /* OP_TIMES  */
        S_OP_DIVIDE(24), /* OP_DIVIDE  */
        S_HEAD(25), /* HEAD  */
        S_TAIL(26), /* TAIL  */
        S_CONS(27), /* CONS  */
        S_EQUAL(28), /* EQUAL  */
        S_NONEQUAL(29), /* NONEQUAL  */
        S_LESS(30), /* LESS  */
        S_LESSEQ(31), /* LESSEQ  */
        S_GREATER(32), /* GREATER  */
        S_GREATEREQ(33), /* GREATEREQ  */
        S_ISINT(34), /* ISINT  */
        S_ISREAL(35), /* ISREAL  */
        S_ISBOOL(36), /* ISBOOL  */
        S_ISNULL(37), /* ISNULL  */
        S_ISATOM(38), /* ISATOM  */
        S_ISLIST(39), /* ISLIST  */
        S_AND(40), /* AND  */
        S_OR(41), /* OR  */
        S_XOR(42), /* XOR  */
        S_NOT(43), /* NOT  */
        S_YYACCEPT(44), /* $accept  */
        S_program(45), /* program  */
        S_element(46), /* element  */
        S_literal(47), /* literal  */
        S_quoted_element(48), /* quoted_element  */
        S_operator_symbol(49), /* operator_symbol  */
        S_list(50), /* list  */
        S_element_list_opt(51), /* element_list_opt  */
        S_element_list(52), /* element_list  */
        S_setq_form(53), /* setq_form  */
        S_func_form(54), /* func_form  */
        S_lambda_form(55), /* lambda_form  */
        S_prog_form(56), /* prog_form  */
        S_cond_form(57), /* cond_form  */
        S_while_form(58), /* while_form  */
        S_return_form(59), /* return_form  */
        S_break_form(60), /* break_form  */
        S_parameter_list(61), /* parameter_list  */
        S_identifier_list_opt(62), /* identifier_list_opt  */
        S_identifier_list(63), /* identifier_list  */
        S_element_sequence(64), /* element_sequence  */
        S_arithmetic_call(65), /* arithmetic_call  */
        S_list_call(66), /* list_call  */
        S_comparison_call(67), /* comparison_call  */
        S_predicate_call(68), /* predicate_call  */
        S_logical_call(69), /* logical_call  */
        S_eval_call(70); /* eval_call  */

        private final int yycode_;

        SymbolKind(int n) {
            this.yycode_ = n;
        }

        private static final SymbolKind[] values_ = {
            SymbolKind.S_YYEOF,
            SymbolKind.S_YYerror,
            SymbolKind.S_YYUNDEF,
            SymbolKind.S_QUOTE,
            SymbolKind.S_SETQ,
            SymbolKind.S_FUNC,
            SymbolKind.S_LAMBDA,
            SymbolKind.S_PROG,
            SymbolKind.S_COND,
            SymbolKind.S_WHILE,
            SymbolKind.S_RETURN,
            SymbolKind.S_BREAK,
            SymbolKind.S_EVAL,
            SymbolKind.S_LEFT_PAREN,
            SymbolKind.S_RIGHT_PAREN,
            SymbolKind.S_QUOTE_SYM,
            SymbolKind.S_NULL,
            SymbolKind.S_NUMBER,
            SymbolKind.S_IDENTIFIER,
            SymbolKind.S_TRUE,
            SymbolKind.S_FALSE,
            SymbolKind.S_OP_PLUS,
            SymbolKind.S_OP_MINUS,
            SymbolKind.S_OP_TIMES,
            SymbolKind.S_OP_DIVIDE,
            SymbolKind.S_HEAD,
            SymbolKind.S_TAIL,
            SymbolKind.S_CONS,
            SymbolKind.S_EQUAL,
            SymbolKind.S_NONEQUAL,
            SymbolKind.S_LESS,
            SymbolKind.S_LESSEQ,
            SymbolKind.S_GREATER,
            SymbolKind.S_GREATEREQ,
            SymbolKind.S_ISINT,
            SymbolKind.S_ISREAL,
            SymbolKind.S_ISBOOL,
            SymbolKind.S_ISNULL,
            SymbolKind.S_ISATOM,
            SymbolKind.S_ISLIST,
            SymbolKind.S_AND,
            SymbolKind.S_OR,
            SymbolKind.S_XOR,
            SymbolKind.S_NOT,
            SymbolKind.S_YYACCEPT,
            SymbolKind.S_program,
            SymbolKind.S_element,
            SymbolKind.S_literal,
            SymbolKind.S_quoted_element,
            SymbolKind.S_operator_symbol,
            SymbolKind.S_list,
            SymbolKind.S_element_list_opt,
            SymbolKind.S_element_list,
            SymbolKind.S_setq_form,
            SymbolKind.S_func_form,
            SymbolKind.S_lambda_form,
            SymbolKind.S_prog_form,
            SymbolKind.S_cond_form,
            SymbolKind.S_while_form,
            SymbolKind.S_return_form,
            SymbolKind.S_break_form,
            SymbolKind.S_parameter_list,
            SymbolKind.S_identifier_list_opt,
            SymbolKind.S_identifier_list,
            SymbolKind.S_element_sequence,
            SymbolKind.S_arithmetic_call,
            SymbolKind.S_list_call,
            SymbolKind.S_comparison_call,
            SymbolKind.S_predicate_call,
            SymbolKind.S_logical_call,
            SymbolKind.S_eval_call
        };

        static final SymbolKind get(int code) {
            return values_[code];
        }

        public final int getCode() {
            return this.yycode_;
        }

        /* YYNAMES_[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
        First, the terminals, then, starting at \a YYNTOKENS_, nonterminals.  */
        private static final String[] yynames_ = yynames_init();

        private static final String[] yynames_init() {
            return new String[] {
                "end of file",
                "error",
                "invalid token",
                "QUOTE",
                "SETQ",
                "FUNC",
                "LAMBDA",
                "PROG",
                "COND",
                "WHILE",
                "RETURN",
                "BREAK",
                "EVAL",
                "LEFT_PAREN",
                "RIGHT_PAREN",
                "QUOTE_SYM",
                "NULL",
                "NUMBER",
                "IDENTIFIER",
                "TRUE",
                "FALSE",
                "OP_PLUS",
                "OP_MINUS",
                "OP_TIMES",
                "OP_DIVIDE",
                "HEAD",
                "TAIL",
                "CONS",
                "EQUAL",
                "NONEQUAL",
                "LESS",
                "LESSEQ",
                "GREATER",
                "GREATEREQ",
                "ISINT",
                "ISREAL",
                "ISBOOL",
                "ISNULL",
                "ISATOM",
                "ISLIST",
                "AND",
                "OR",
                "XOR",
                "NOT",
                "$accept",
                "program",
                "element",
                "literal",
                "quoted_element",
                "operator_symbol",
                "list",
                "element_list_opt",
                "element_list",
                "setq_form",
                "func_form",
                "lambda_form",
                "prog_form",
                "cond_form",
                "while_form",
                "return_form",
                "break_form",
                "parameter_list",
                "identifier_list_opt",
                "identifier_list",
                "element_sequence",
                "arithmetic_call",
                "list_call",
                "comparison_call",
                "predicate_call",
                "logical_call",
                "eval_call",
                null
            };
        }

        /* The user-facing name of this symbol.  */
        public final String getName() {
            return yynames_[yycode_];
        }
    };

    /**
     * Communication interface between the scanner and the Bison-generated
     * parser <tt>FParser</tt>.
     */
    public interface Lexer {
        /* Token kinds.  */
        /** Token "end of file", to be returned by the scanner.  */
        static final int YYEOF = 0;

        /** Token error, to be returned by the scanner.  */
        static final int YYerror = 256;

        /** Token "invalid token", to be returned by the scanner.  */
        static final int YYUNDEF = 257;

        /** Token QUOTE, to be returned by the scanner.  */
        static final int QUOTE = 258;

        /** Token SETQ, to be returned by the scanner.  */
        static final int SETQ = 259;

        /** Token FUNC, to be returned by the scanner.  */
        static final int FUNC = 260;

        /** Token LAMBDA, to be returned by the scanner.  */
        static final int LAMBDA = 261;

        /** Token PROG, to be returned by the scanner.  */
        static final int PROG = 262;

        /** Token COND, to be returned by the scanner.  */
        static final int COND = 263;

        /** Token WHILE, to be returned by the scanner.  */
        static final int WHILE = 264;

        /** Token RETURN, to be returned by the scanner.  */
        static final int RETURN = 265;

        /** Token BREAK, to be returned by the scanner.  */
        static final int BREAK = 266;

        /** Token EVAL, to be returned by the scanner.  */
        static final int EVAL = 267;

        /** Token LEFT_PAREN, to be returned by the scanner.  */
        static final int LEFT_PAREN = 268;

        /** Token RIGHT_PAREN, to be returned by the scanner.  */
        static final int RIGHT_PAREN = 269;

        /** Token QUOTE_SYM, to be returned by the scanner.  */
        static final int QUOTE_SYM = 270;

        /** Token NULL, to be returned by the scanner.  */
        static final int NULL = 271;

        /** Token NUMBER, to be returned by the scanner.  */
        static final int NUMBER = 272;

        /** Token IDENTIFIER, to be returned by the scanner.  */
        static final int IDENTIFIER = 273;

        /** Token TRUE, to be returned by the scanner.  */
        static final int TRUE = 274;

        /** Token FALSE, to be returned by the scanner.  */
        static final int FALSE = 275;

        /** Token OP_PLUS, to be returned by the scanner.  */
        static final int OP_PLUS = 276;

        /** Token OP_MINUS, to be returned by the scanner.  */
        static final int OP_MINUS = 277;

        /** Token OP_TIMES, to be returned by the scanner.  */
        static final int OP_TIMES = 278;

        /** Token OP_DIVIDE, to be returned by the scanner.  */
        static final int OP_DIVIDE = 279;

        /** Token HEAD, to be returned by the scanner.  */
        static final int HEAD = 280;

        /** Token TAIL, to be returned by the scanner.  */
        static final int TAIL = 281;

        /** Token CONS, to be returned by the scanner.  */
        static final int CONS = 282;

        /** Token EQUAL, to be returned by the scanner.  */
        static final int EQUAL = 283;

        /** Token NONEQUAL, to be returned by the scanner.  */
        static final int NONEQUAL = 284;

        /** Token LESS, to be returned by the scanner.  */
        static final int LESS = 285;

        /** Token LESSEQ, to be returned by the scanner.  */
        static final int LESSEQ = 286;

        /** Token GREATER, to be returned by the scanner.  */
        static final int GREATER = 287;

        /** Token GREATEREQ, to be returned by the scanner.  */
        static final int GREATEREQ = 288;

        /** Token ISINT, to be returned by the scanner.  */
        static final int ISINT = 289;

        /** Token ISREAL, to be returned by the scanner.  */
        static final int ISREAL = 290;

        /** Token ISBOOL, to be returned by the scanner.  */
        static final int ISBOOL = 291;

        /** Token ISNULL, to be returned by the scanner.  */
        static final int ISNULL = 292;

        /** Token ISATOM, to be returned by the scanner.  */
        static final int ISATOM = 293;

        /** Token ISLIST, to be returned by the scanner.  */
        static final int ISLIST = 294;

        /** Token AND, to be returned by the scanner.  */
        static final int AND = 295;

        /** Token OR, to be returned by the scanner.  */
        static final int OR = 296;

        /** Token XOR, to be returned by the scanner.  */
        static final int XOR = 297;

        /** Token NOT, to be returned by the scanner.  */
        static final int NOT = 298;

        /** Deprecated, use YYEOF instead.  */
        public static final int EOF = YYEOF;

        /**
         * Method to retrieve the semantic value of the last scanned token.
         * @return the semantic value of the last scanned token.
         */
        Object getLVal();

        /**
         * Entry point for the scanner.  Returns the token identifier corresponding
         * to the next token and prepares to return the semantic value
         * of the token.
         * @return the token identifier corresponding to the next token.
         */
        int yylex() throws java.io.IOException;

        /**
         * Emit an errorin a user-defined way.
         *
         *
         * @param msg The string for the error message.
         */
        void yyerror(String msg);
    }

    /**
     * The object doing lexical analysis for us.
     */
    private Lexer yylexer;

    /**
     * Instantiates the Bison-generated parser.
     * @param yylexer The scanner that will supply tokens to the parser.
     */
    public FParser(Lexer yylexer) {

        this.yylexer = yylexer;
    }

    private int yynerrs = 0;

    /**
     * The number of syntax errors so far.
     */
    public final int getNumberOfErrors() {
        return yynerrs;
    }

    /**
     * Print an error message via the lexer.
     *
     * @param msg The error message.
     */
    public final void yyerror(String msg) {
        yylexer.yyerror(msg);
    }

    private final class YYStack {
        private int[] stateStack = new int[16];
        private Object[] valueStack = new Object[16];

        public int size = 16;
        public int height = -1;

        public final void push(int state, Object value) {
            height++;
            if (size == height) {
                int[] newStateStack = new int[size * 2];
                System.arraycopy(stateStack, 0, newStateStack, 0, height);
                stateStack = newStateStack;

                Object[] newValueStack = new Object[size * 2];
                System.arraycopy(valueStack, 0, newValueStack, 0, height);
                valueStack = newValueStack;

                size *= 2;
            }

            stateStack[height] = state;
            valueStack[height] = value;
        }

        public final void pop() {
            pop(1);
        }

        public final void pop(int num) {
            // Avoid memory leaks... garbage collection is a white lie!
            if (0 < num) {
                java.util.Arrays.fill(valueStack, height - num + 1, height + 1, null);
            }
            height -= num;
        }

        public final int stateAt(int i) {
            return stateStack[height - i];
        }

        public final Object valueAt(int i) {
            return valueStack[height - i];
        }

        // Print the state stack on the debug stream.
        public void print(java.io.PrintStream out) {
            out.print("Stack now");

            for (int i = 0; i <= height; i++) {
                out.print(' ');
                out.print(stateStack[i]);
            }
            out.println();
        }
    }

    /**
     * Returned by a Bison action in order to stop the parsing process and
     * return success (<tt>true</tt>).
     */
    public static final int YYACCEPT = 0;

    /**
     * Returned by a Bison action in order to stop the parsing process and
     * return failure (<tt>false</tt>).
     */
    public static final int YYABORT = 1;

    /**
     * Returned by a Bison action in order to start error recovery without
     * printing an error message.
     */
    public static final int YYERROR = 2;

    /**
     * Internal return codes that are not supported for user semantic
     * actions.
     */
    private static final int YYERRLAB = 3;

    private static final int YYNEWSTATE = 4;
    private static final int YYDEFAULT = 5;
    private static final int YYREDUCE = 6;
    private static final int YYERRLAB1 = 7;
    private static final int YYRETURN = 8;

    private int yyerrstatus_ = 0;

    /**
     * Whether error recovery is being done.  In this state, the parser
     * reads token until it reaches a known state, and then restarts normal
     * operation.
     */
    public final boolean recovering() {
        return yyerrstatus_ == 0;
    }

    /** Compute post-reduction state.
     * @param yystate   the current state
     * @param yysym     the nonterminal to push on the stack
     */
    private int yyLRGotoState(int yystate, int yysym) {
        int yyr = yypgoto_[yysym - YYNTOKENS_] + yystate;
        if (0 <= yyr && yyr <= YYLAST_ && yycheck_[yyr] == yystate) return yytable_[yyr];
        else return yydefgoto_[yysym - YYNTOKENS_];
    }

    private int yyaction(int yyn, YYStack yystack, int yylen) {
        /* If YYLEN is nonzero, implement the default value of the action:
        '$$ = $1'.  Otherwise, use the top of the stack.

        Otherwise, the following line sets YYVAL to garbage.
        This behavior is undocumented and Bison
        users should not rely upon it.  */
        Object yyval = (0 < yylen) ? yystack.valueAt(yylen - 1) : yystack.valueAt(0);

        switch (yyn) {

            /* "./app/src/main/java/ru/lapolitia/parser/FParser.java":565  */

            default:
                break;
        }

        yystack.pop(yylen);
        yylen = 0;
        /* Shift the result of the reduction.  */
        int yystate = yyLRGotoState(yystack.stateAt(0), yyr1_[yyn]);
        yystack.push(yystate, yyval);
        return YYNEWSTATE;
    }

    /**
     * Parse input from the scanner that was specified at object construction
     * time.  Return whether the end of the input was reached successfully.
     *
     * @return <tt>true</tt> if the parsing succeeds.  Note that this does not
     *          imply that there were no syntax errors.
     */
    public boolean parse() throws java.io.IOException {

        /* Lookahead token kind.  */
        int yychar = YYEMPTY_;
        /* Lookahead symbol kind.  */
        SymbolKind yytoken = null;

        /* State.  */
        int yyn = 0;
        int yylen = 0;
        int yystate = 0;
        YYStack yystack = new YYStack();
        int label = YYNEWSTATE;

        /* Semantic value of the lookahead.  */
        Object yylval = null;

        yyerrstatus_ = 0;
        yynerrs = 0;

        /* Initialize the stack.  */
        yystack.push(yystate, yylval);

        for (; ; )
            switch (label) {
                /* New state.  Unlike in the C/C++ skeletons, the state is already
                pushed when we come here.  */
                case YYNEWSTATE:

                    /* Accept?  */
                    if (yystate == YYFINAL_) return true;

                    /* Take a decision.  First try without lookahead.  */
                    yyn = yypact_[yystate];
                    if (yyPactValueIsDefault(yyn)) {
                        label = YYDEFAULT;
                        break;
                    }

                    /* Read a lookahead token.  */
                    if (yychar == YYEMPTY_) {

                        yychar = yylexer.yylex();
                        yylval = yylexer.getLVal();
                    }

                    /* Convert token to internal form.  */
                    yytoken = yytranslate_(yychar);

                    if (yytoken == SymbolKind.S_YYerror) {
                        // The scanner already issued an error message, process directly
                        // to error recovery.  But do not keep the error token as
                        // lookahead, it is too special and may lead us to an endless
                        // loop in error recovery. */
                        yychar = Lexer.YYUNDEF;
                        yytoken = SymbolKind.S_YYUNDEF;
                        label = YYERRLAB1;
                    } else {
                        /* If the proper action on seeing token YYTOKEN is to reduce or to
                        detect an error, take that action.  */
                        yyn += yytoken.getCode();
                        if (yyn < 0 || YYLAST_ < yyn || yycheck_[yyn] != yytoken.getCode()) {
                            label = YYDEFAULT;
                        }

                        /* <= 0 means reduce or error.  */
                        else if ((yyn = yytable_[yyn]) <= 0) {
                            if (yyTableValueIsError(yyn)) {
                                label = YYERRLAB;
                            } else {
                                yyn = -yyn;
                                label = YYREDUCE;
                            }
                        } else {
                            /* Shift the lookahead token.  */
                            /* Discard the token being shifted.  */
                            yychar = YYEMPTY_;

                            /* Count tokens shifted since error; after three, turn off error
                            status.  */
                            if (yyerrstatus_ > 0) --yyerrstatus_;

                            yystate = yyn;
                            yystack.push(yystate, yylval);
                            label = YYNEWSTATE;
                        }
                    }
                    break;

                /*-----------------------------------------------------------.
                | yydefault -- do the default action for the current state.  |
                `-----------------------------------------------------------*/
                case YYDEFAULT:
                    yyn = yydefact_[yystate];
                    if (yyn == 0) label = YYERRLAB;
                    else label = YYREDUCE;
                    break;

                /*-----------------------------.
                | yyreduce -- Do a reduction.  |
                `-----------------------------*/
                case YYREDUCE:
                    yylen = yyr2_[yyn];
                    label = yyaction(yyn, yystack, yylen);
                    yystate = yystack.stateAt(0);
                    break;

                /*------------------------------------.
                | yyerrlab -- here on detecting error |
                `------------------------------------*/
                case YYERRLAB:
                    /* If not already recovering from an error, report this error.  */
                    if (yyerrstatus_ == 0) {
                        ++yynerrs;
                        if (yychar == YYEMPTY_) yytoken = null;
                        yyreportSyntaxError(new Context(this, yystack, yytoken));
                    }

                    if (yyerrstatus_ == 3) {
                        /* If just tried and failed to reuse lookahead token after an
                        error, discard it.  */

                        if (yychar <= Lexer.YYEOF) {
                            /* Return failure if at end of input.  */
                            if (yychar == Lexer.YYEOF) return false;
                        } else yychar = YYEMPTY_;
                    }

                    /* Else will try to reuse lookahead token after shifting the error
                    token.  */
                    label = YYERRLAB1;
                    break;

                /*-------------------------------------------------.
                | errorlab -- error raised explicitly by YYERROR.  |
                `-------------------------------------------------*/
                case YYERROR:
                    /* Do not reclaim the symbols of the rule which action triggered
                    this YYERROR.  */
                    yystack.pop(yylen);
                    yylen = 0;
                    yystate = yystack.stateAt(0);
                    label = YYERRLAB1;
                    break;

                /*-------------------------------------------------------------.
                | yyerrlab1 -- common code for both syntax error and YYERROR.  |
                `-------------------------------------------------------------*/
                case YYERRLAB1:
                    yyerrstatus_ = 3; /* Each real token shifted decrements this.  */

                    // Pop stack until we find a state that shifts the error token.
                    for (; ; ) {
                        yyn = yypact_[yystate];
                        if (!yyPactValueIsDefault(yyn)) {
                            yyn += SymbolKind.S_YYerror.getCode();
                            if (0 <= yyn
                                    && yyn <= YYLAST_
                                    && yycheck_[yyn] == SymbolKind.S_YYerror.getCode()) {
                                yyn = yytable_[yyn];
                                if (0 < yyn) break;
                            }
                        }

                        /* Pop the current state because it cannot handle the
                         * error token.  */
                        if (yystack.height == 0) return false;

                        yystack.pop();
                        yystate = yystack.stateAt(0);
                    }

                    if (label == YYABORT)
                        /* Leave the switch.  */
                        break;

                    /* Shift the error token.  */

                    yystate = yyn;
                    yystack.push(yyn, yylval);
                    label = YYNEWSTATE;
                    break;

                /* Accept.  */
                case YYACCEPT:
                    return true;

                /* Abort.  */
                case YYABORT:
                    return false;
            }
    }

    /**
     * Information needed to get the list of expected tokens and to forge
     * a syntax error diagnostic.
     */
    public static final class Context {
        Context(FParser parser, YYStack stack, SymbolKind token) {
            yyparser = parser;
            yystack = stack;
            yytoken = token;
        }

        private FParser yyparser;
        private YYStack yystack;

        /**
         * The symbol kind of the lookahead token.
         */
        public final SymbolKind getToken() {
            return yytoken;
        }

        private SymbolKind yytoken;
        static final int NTOKENS = FParser.YYNTOKENS_;

        /**
         * Put in YYARG at most YYARGN of the expected tokens given the
         * current YYCTX, and return the number of tokens stored in YYARG.  If
         * YYARG is null, return the number of expected tokens (guaranteed to
         * be less than YYNTOKENS).
         */
        int getExpectedTokens(SymbolKind yyarg[], int yyargn) {
            return getExpectedTokens(yyarg, 0, yyargn);
        }

        int getExpectedTokens(SymbolKind yyarg[], int yyoffset, int yyargn) {
            int yycount = yyoffset;
            int yyn = yypact_[this.yystack.stateAt(0)];
            if (!yyPactValueIsDefault(yyn)) {
                /* Start YYX at -YYN if negative to avoid negative
                indexes in YYCHECK.  In other words, skip the first
                -YYN actions for this state because they are default
                actions.  */
                int yyxbegin = yyn < 0 ? -yyn : 0;
                /* Stay within bounds of both yycheck and yytname.  */
                int yychecklim = YYLAST_ - yyn + 1;
                int yyxend = yychecklim < NTOKENS ? yychecklim : NTOKENS;
                for (int yyx = yyxbegin; yyx < yyxend; ++yyx)
                    if (yycheck_[yyx + yyn] == yyx
                            && yyx != SymbolKind.S_YYerror.getCode()
                            && !yyTableValueIsError(yytable_[yyx + yyn])) {
                        if (yyarg == null) yycount += 1;
                        else if (yycount == yyargn) return 0; // FIXME: this is incorrect.
                        else yyarg[yycount++] = SymbolKind.get(yyx);
                    }
            }
            if (yyarg != null && yycount == yyoffset && yyoffset < yyargn) yyarg[yycount] = null;
            return yycount - yyoffset;
        }
    }

    private int yysyntaxErrorArguments(Context yyctx, SymbolKind[] yyarg, int yyargn) {
        /* There are many possibilities here to consider:
           - If this state is a consistent state with a default action,
             then the only way this function was invoked is if the
             default action is an error action.  In that case, don't
             check for expected tokens because there are none.
           - The only way there can be no lookahead present (in tok) is
             if this state is a consistent state with a default action.
             Thus, detecting the absence of a lookahead is sufficient to
             determine that there is no unexpected or expected token to
             report.  In that case, just report a simple "syntax error".
           - Don't assume there isn't a lookahead just because this
             state is a consistent state with a default action.  There
             might have been a previous inconsistent state, consistent
             state with a non-default action, or user semantic action
             that manipulated yychar.  (However, yychar is currently out
             of scope during semantic actions.)
           - Of course, the expected token list depends on states to
             have correct lookahead information, and it depends on the
             parser not to perform extra reductions after fetching a
             lookahead from the scanner and before detecting a syntax
             error.  Thus, state merging (from LALR or IELR) and default
             reductions corrupt the expected token list.  However, the
             list is correct for canonical LR with one exception: it
             will still contain any token that will not be accepted due
             to an error action in a later state.
        */
        int yycount = 0;
        if (yyctx.getToken() != null) {
            if (yyarg != null) yyarg[yycount] = yyctx.getToken();
            yycount += 1;
            yycount += yyctx.getExpectedTokens(yyarg, 1, yyargn);
        }
        return yycount;
    }

    /**
     * Build and emit a "syntax error" message in a user-defined way.
     *
     * @param ctx  The context of the error.
     */
    private void yyreportSyntaxError(Context yyctx) {
        if (yyErrorVerbose) {
            final int argmax = 5;
            SymbolKind[] yyarg = new SymbolKind[argmax];
            int yycount = yysyntaxErrorArguments(yyctx, yyarg, argmax);
            String[] yystr = new String[yycount];
            for (int yyi = 0; yyi < yycount; ++yyi) {
                yystr[yyi] = yyarg[yyi].getName();
            }
            String yyformat;
            switch (yycount) {
                default:
                case 0:
                    yyformat = "syntax error";
                    break;
                case 1:
                    yyformat = "syntax error, unexpected {0}";
                    break;
                case 2:
                    yyformat = "syntax error, unexpected {0}, expecting {1}";
                    break;
                case 3:
                    yyformat = "syntax error, unexpected {0}, expecting {1} or {2}";
                    break;
                case 4:
                    yyformat = "syntax error, unexpected {0}, expecting {1} or {2} or {3}";
                    break;
                case 5:
                    yyformat = "syntax error, unexpected {0}, expecting {1} or {2} or {3} or {4}";
                    break;
            }
            yyerror(new MessageFormat(yyformat).format(yystr));
        } else {
            yyerror("syntax error");
        }
    }

    /**
     * Whether the given <code>yypact_</code> value indicates a defaulted state.
     * @param yyvalue   the value to check
     */
    private static boolean yyPactValueIsDefault(int yyvalue) {
        return yyvalue == yypact_ninf_;
    }

    /**
     * Whether the given <code>yytable_</code>
     * value indicates a syntax error.
     * @param yyvalue the value to check
     */
    private static boolean yyTableValueIsError(int yyvalue) {
        return yyvalue == yytable_ninf_;
    }

    private static final short yypact_ninf_ = -17;
    private static final short yytable_ninf_ = -1;

    /* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
    STATE-NUM.  */
    private static final short[] yypact_ = yypact_init();

    private static final short[] yypact_init() {
        return new short[] {
            156, 113, 144, -17, -17, -17, -17, -17, 3, -17, -17, -17, -17, -17, -17, -17, -17, -17,
            -17, -17, -17, -17, -17, -17, -17, -17, -17, 156, -16, -13, -9, -9, 156, 156, 156, -8,
            156, 156, 156, 156, 156, 156, 156, 156, 156, 156, 156, 156, 156, 156, 156, 156, 156,
            156, 156, 156, 156, 156, 156, 156, -17, 10, 156, -17, -17, -17, -17, -17, -17, -17, -17,
            11, 156, -9, 9, 156, 156, 156, 156, 14, -17, 15, 156, 156, 156, 156, 16, 20, 156, 156,
            156, 156, 156, 156, 156, 46, 56, 58, 59, 64, 65, 156, 156, 156, 66, -17, -17, -17, 71,
            156, -17, 72, 76, 81, -17, -5, 49, 82, -17, -17, 83, 84, 85, 89, -17, -17, 90, 91, 92,
            93, 95, 96, 97, -17, -17, -17, -17, -17, -17, 98, 99, 163, -17, -17, 164, -17, -17, -17,
            -17, -17, -17, 165, -17, -17, -17, -17, -17, -17, -17, -17, -17, -17, -17, -17, -17,
            -17, -17, -17, -17
        };
    }

    /* YYDEFACT[STATE-NUM] -- Default reduction number in state STATE-NUM.
    Performed when YYTABLE does not specify something else to do.  Zero
    means the default is an error.  */
    private static final byte[] yydefact_ = yydefact_init();

    private static final byte[] yydefact_init() {
        return new byte[] {
            0, 34, 0, 11, 8, 4, 9, 10, 0, 2, 5, 7, 6, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
            31, 32, 33, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 36, 0, 35, 15, 16, 17, 18, 12, 13, 1, 3, 0, 0, 0, 48, 0, 0, 0,
            0, 0, 46, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 19,
            37, 14, 0, 0, 50, 0, 49, 0, 52, 0, 0, 0, 45, 77, 0, 0, 0, 0, 58, 59, 0, 0, 0, 0, 0, 0,
            0, 67, 68, 69, 70, 71, 72, 0, 0, 0, 76, 38, 0, 47, 51, 40, 41, 53, 42, 0, 44, 54, 55,
            56, 57, 60, 61, 62, 63, 64, 65, 66, 73, 74, 75, 39, 43
        };
    }

    /* YYPGOTO[NTERM-NUM].  */
    private static final byte[] yypgoto_ = yypgoto_init();

    private static final byte[] yypgoto_init() {
        return new byte[] {
            -17, -17, -1, -17, -17, -17, -17, -17, -17, -17,
            -17, -17, -17, -17, -17, -17, -17, -14, -17, -17,
            -17, -17, -17, -17, -17, -17, -17
        };
    }

    /* YYDEFGOTO[NTERM-NUM].  */
    private static final byte[] yydefgoto_ = yydefgoto_init();

    private static final byte[] yydefgoto_init() {
        return new byte[] {
            0, 8, 9, 10, 11, 68, 12, 61, 62, 13, 14, 15, 16, 17, 18, 19, 20, 75, 111, 112, 115, 21,
            22, 23, 24, 25, 26
        };
    }

    /* YYTABLE[YYPACT[STATE-NUM]] -- What to do in state STATE-NUM.  If
    positive, shift that token.  If negative, reduce the rule whose
    number is the opposite.  If YYTABLE_NINF, syntax error.  */
    private static final short[] yytable_ = yytable_init();

    private static final short[] yytable_init() {
        return new short[] {
            60, 67, 72, 69, 74, 73, 80, 70, 1, 148, 2, 3, 4, 5, 6, 7, 1, 76, 2, 3, 4, 5, 6, 7, 105,
            107, 71, 110, 118, 119, 124, 77, 78, 79, 125, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90,
            91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 109, 133, 106, 1, 150, 2,
            3, 4, 5, 6, 7, 134, 108, 135, 136, 113, 114, 116, 117, 137, 138, 142, 120, 121, 122,
            123, 143, 145, 126, 127, 128, 129, 130, 131, 132, 146, 147, 152, 153, 154, 155, 139,
            140, 141, 156, 157, 158, 159, 160, 144, 161, 162, 163, 164, 165, 149, 151, 27, 28, 29,
            30, 31, 32, 33, 34, 35, 36, 1, 0, 2, 3, 4, 5, 6, 7, 37, 38, 39, 40, 41, 42, 43, 44, 45,
            46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 1, 0, 2, 3, 4, 5, 6, 7, 63, 64,
            65, 66, 1, 0, 2, 3, 4, 5, 6, 7, 166, 167, 168
        };
    }

    private static final byte[] yycheck_ = yycheck_init();

    private static final byte[] yycheck_init() {
        return new byte[] {
            1, 2, 18, 0, 13, 18, 14, 8, 13, 14, 15, 16, 17, 18, 19, 20, 13, 31, 15, 16, 17, 18, 19,
            20, 14, 14, 27, 18, 14, 14, 14, 32, 33, 34, 14, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45,
            46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 73, 14, 62, 13, 14, 15, 16, 17,
            18, 19, 20, 14, 72, 14, 14, 75, 76, 77, 78, 14, 14, 14, 82, 83, 84, 85, 14, 14, 88, 89,
            90, 91, 92, 93, 94, 18, 14, 14, 14, 14, 14, 101, 102, 103, 14, 14, 14, 14, 14, 109, 14,
            14, 14, 14, 14, 115, 116, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, -1, 15, 16, 17, 18, 19,
            20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41,
            42, 43, 13, -1, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 13, -1, 15, 16, 17, 18, 19, 20,
            14, 14, 14
        };
    }

    /* YYSTOS[STATE-NUM] -- The symbol kind of the accessing symbol of
    state STATE-NUM.  */
    private static final byte[] yystos_ = yystos_init();

    private static final byte[] yystos_init() {
        return new byte[] {
            0, 13, 15, 16, 17, 18, 19, 20, 45, 46, 47, 48, 50, 53, 54, 55, 56, 57, 58, 59, 60, 65,
            66, 67, 68, 69, 70, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 21, 22, 23, 24, 25, 26, 27, 28, 29,
            30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 46, 51, 52, 21, 22, 23, 24, 46,
            49, 0, 46, 46, 18, 18, 13, 61, 61, 46, 46, 46, 14, 46, 46, 46, 46, 46, 46, 46, 46, 46,
            46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 14, 46, 14, 46, 61, 18, 62,
            63, 46, 46, 64, 46, 46, 14, 14, 46, 46, 46, 46, 14, 14, 46, 46, 46, 46, 46, 46, 46, 14,
            14, 14, 14, 14, 14, 46, 46, 46, 14, 14, 46, 14, 18, 14, 14, 46, 14, 46, 14, 14, 14, 14,
            14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14
        };
    }

    /* YYR1[RULE-NUM] -- Symbol kind of the left-hand side of rule RULE-NUM.  */
    private static final byte[] yyr1_ = yyr1_init();

    private static final byte[] yyr1_init() {
        return new byte[] {
            0, 44, 45, 45, 46, 46, 46, 46, 47, 47, 47, 47, 48, 48, 48, 49, 49, 49, 49, 50, 50, 50,
            50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 51, 51, 52, 52, 53, 54, 55, 56, 57, 57,
            58, 59, 60, 61, 62, 62, 63, 63, 64, 64, 65, 65, 65, 65, 66, 66, 66, 67, 67, 67, 67, 67,
            67, 68, 68, 68, 68, 68, 68, 69, 69, 69, 69, 70
        };
    }

    /* YYR2[RULE-NUM] -- Number of symbols on the right-hand side of rule RULE-NUM.  */
    private static final byte[] yyr2_ = yyr2_init();

    private static final byte[] yyr2_init() {
        return new byte[] {
            0, 2, 1, 2, 1, 1, 1, 1, 1, 1,
            1, 1, 2, 2, 4, 1, 1, 1, 1, 3,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 0, 1, 1, 2, 5, 6,
            5, 5, 5, 6, 5, 4, 3, 3, 0, 1,
            1, 2, 1, 2, 5, 5, 5, 5, 4, 4,
            5, 5, 5, 5, 5, 5, 5, 4, 4, 4,
            4, 4, 4, 5, 5, 5, 4, 4
        };
    }

    /* YYTRANSLATE_(TOKEN-NUM) -- Symbol number corresponding to TOKEN-NUM
    as returned by yylex, with out-of-bounds checking.  */
    private static final SymbolKind yytranslate_(int t) {
        // Last valid token kind.
        int code_max = 298;
        if (t <= 0) return SymbolKind.S_YYEOF;
        else if (t <= code_max) return SymbolKind.get(yytranslate_table_[t]);
        else return SymbolKind.S_YYUNDEF;
    }

    private static final byte[] yytranslate_table_ = yytranslate_table_init();

    private static final byte[] yytranslate_table_init() {
        return new byte[] {
            0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 3, 4, 5,
            6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28,
            29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43
        };
    }

    private static final int YYLAST_ = 179;
    private static final int YYEMPTY_ = -2;
    private static final int YYFINAL_ = 69;
    private static final int YYNTOKENS_ = 44;
}
/* "bison/FParser.y":185  */
