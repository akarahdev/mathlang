package net.realmofuz.lexer;

public sealed interface Token {
    record Number(String value, Span span) implements Token {
    }
    record Symbol(String value, Span span) implements Token {
    }
    record Keyword(String value, Span span) implements Token {
    }
    record OpenParen(Span span) implements Token {
    }
    record CloseParen(Span span) implements Token {
    }
    record OpenBrace(Span span) implements Token {
    }
    record CloseBrace(Span span) implements Token {
    }
    record OpenBracket(Span span) implements Token {
    }
    record CloseBracket(Span span) implements Token {
    }
    record Semicolon(Span span) implements Token {
    }
    record Colon(Span span) implements Token {
    }
    record Comma(Span span) implements Token {
    }
    record EOF(Span span) implements Token {
    }

    Span span();
}

