package net.realmofuz.lexer;

public record Span(
    int line,
    int row,
    String fileName,
    String sourceText
) {}
