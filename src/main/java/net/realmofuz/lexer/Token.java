package net.realmofuz.lexer;

public sealed interface Token {
    record Number(String value) implements Token {
    }
    record Symbol(String value) implements Token {
    }
    record Equals() implements Token {
    }
}

