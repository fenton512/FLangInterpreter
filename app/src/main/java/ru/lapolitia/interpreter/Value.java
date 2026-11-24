package ru.lapolitia.interpreter;

/** Runtime value of a FLang program. */
public sealed interface Value
        permits IntValue,
                RealValue,
                BoolValue,
                NullValue,
                ListValue,
                SymbolValue,
                ClosureValue,
                BuiltinValue,
                CodeValue {}
