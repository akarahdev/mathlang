package net.realmofuz.lexer;

public sealed interface Token {
    record Number(String value) implements Token {
    }
    record Symbol(String value) implements Token {
    }
    record Keyword(String value) implements Token {
    }
    record OpenParen() implements Token {
    }
    record CloseParen() implements Token {
    }
    record OpenBrace() implements Token {
    }
    record CloseBrace() implements Token {
    }
    record OpenBracket() implements Token {
    }
    record CloseBracket() implements Token {
    }
    record Semicolon() implements Token {
    }
    record Colon() implements Token {
    }
    record Comma() implements Token {
    }
}

