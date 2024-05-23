package net.realmofuz.codegen;

import net.realmofuz.lexer.Span;

import java.util.List;

public class CompileException extends RuntimeException {
    CompileError error;
    Span span;
    String help;
    String note;

    public CompileException(
        CompileError error,
        Span span
    ) {
        this.error = error;
        this.span = span;
    }

    public CompileException(
        CompileError error,
        Span span,
        String help
    ) {
        this.error = error;
        this.span = span;
        this.help = help;
    }

    public CompileException(
        CompileError error,
        Span span,
        String help,
        String note
    ) {
        this.error = error;
        this.span = span;
        this.help = help;
        this.note = note;
    }

    public String emit() {
        var line = this.span.sourceText().split("\n")[this.span.line()];
        var sb = new StringBuilder();
        sb.append(STR."/!\\ | \{this.error.message()}\n");
        sb.append("    |\n");
        sb.append(STR."\{this.span.line()+1}   | \{line}\n");
        sb.append(STR."    | \{" ".repeat(this.span.row())}^\n");
        if(help != null)
            sb.append(STR."= help: \{help}");
        if(note != null)
            sb.append(STR."= note: \{note}");

        return sb.toString();
    }
}
