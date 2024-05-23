package net.realmofuz.lexer;

import net.realmofuz.util.Result;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    public String text;
    public List<Token> tokenList = new ArrayList<>();
    int index = -1;

    String fileName;

    public int findLine(int index) {
        var line = 0;
        var ci = 0;
        for(var ch : this.text.toCharArray()) {
            if(ci >= index)
                return line;
            ci++;
            if(ch == '\n')
                line += 1;
        }
        return line;
    }

    public int findColumn(int index) {
        var line = 0;
        var ci = 0;
        var ahead = 0;
        for(var ch : this.text.toCharArray()) {
            if(ci >= index)
                return ahead;
            ci++;
            ahead++;
            if(ch == '\n')
                ahead = 0;
        }
        return line;
    }

    public Lexer(String text) {
        this.text = text;
    }

    public static List<Token> lex(String input, String fileName) {
        var lexer = new Lexer(input);
        lexer.fileName = fileName;

        var sl = lexer.startLexing();

        return sl;
    }

    public char read() {
        do index++;
        while (Character.isWhitespace(this.text.charAt(index)));
        return this.text.charAt(index);
    }

    public char read(int ahead) {
        for(var i = 0; i<ahead; i++) {
            do index++;
            while (Character.isWhitespace(this.text.charAt(index)));
        }
        return this.text.charAt(index);
    }

    public char peek() {
        var f = index + 1;
        while (Character.isWhitespace(this.text.charAt(f)))
            f++;
        return this.text.charAt(f);
    }

    public char peek(int ahead) {
        var f = index;
        for(var i = 0; i<ahead; i++) {
            do f++;
            while (Character.isWhitespace(this.text.charAt(f)));
        }
        return this.text.charAt(f);
    }

    public List<Token> startLexing() {
        while (true) {
            var token = parseToken();
            if (token.isOk()) {
                this.tokenList.add(token.unwrap());
            } else {
                break;
            }
        }
        return this.tokenList;
    }

    public boolean stringAhead(String check) {
        var charIndex = 1;
        for(var ch : check.toCharArray()) {
            if(ch != peek(charIndex)) {
                return false;
            }
            charIndex++;
        }
        this.read(check.length());
        return true;
    }

    public Result<Token, Void> parseToken() {
        try {
            var keywords = new String[]{
                    "struct",
                    "if",
                    "support",

                    "do",
                    "switch",

                    "sqrt",
                    "sin",
                    "cos",
                    "tan",
                    "real",
                    "imag",

                    "->"
            };

            for (var kw : keywords) {
                if (stringAhead(kw)) {
                    return new Result.Ok<>(new Token.Keyword(kw,
                        new Span(findLine(this.index), findColumn(this.index), this.fileName, this.text)));
                }
            }

            if ("0123456789".contains(String.valueOf(this.peek()))) {
                var sb = new StringBuilder();
                try {
                    while ("0123456789".contains(String.valueOf(this.peek())))
                        sb.append(this.read());
                } catch (StringIndexOutOfBoundsException _) {
                }

                return new Result.Ok<>(new Token.Number(
                        sb.toString(),
                    new Span(findLine(this.index), findColumn(this.index), this.fileName, this.text)));
            }

            var operatorString = "+-*/=^?";
            if (operatorString.contains(String.valueOf(this.peek()))) {
                return new Result.Ok<>(new Token.Symbol(String.valueOf(this.read()),
                    new Span(findLine(this.index), findColumn(this.index), this.fileName, this.text)));
            }

            var identString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_";
            if (identString.contains(String.valueOf(this.peek()))) {
                var sb = new StringBuilder();
                while(identString.contains(String.valueOf(this.peek())))
                    sb.append(this.read());
                return new Result.Ok<>(new Token.Symbol(sb.toString(),
                    new Span(findLine(this.index), findColumn(this.index), this.fileName, this.text)));
            }

            if ("(".contains(String.valueOf(this.peek()))) {
                this.read();
                return new Result.Ok<>(new Token.OpenParen(
                    new Span(findLine(this.index), findColumn(this.index), this.fileName, this.text)));
            }
            if (")".contains(String.valueOf(this.peek()))) {
                this.read();
                return new Result.Ok<>(new Token.CloseParen(
                    new Span(findLine(this.index), findColumn(this.index), this.fileName, this.text)));
            }
            if ("{".contains(String.valueOf(this.peek()))) {
                this.read();
                return new Result.Ok<>(new Token.OpenBrace(
                    new Span(findLine(this.index), findColumn(this.index), this.fileName, this.text)));
            }
            if ("}".contains(String.valueOf(this.peek()))) {
                this.read();
                return new Result.Ok<>(new Token.CloseBrace(
                    new Span(findLine(this.index), findColumn(this.index), this.fileName, this.text)));
            }
            if ("[".contains(String.valueOf(this.peek()))) {
                this.read();
                return new Result.Ok<>(new Token.OpenBracket(
                    new Span(findLine(this.index), findColumn(this.index), this.fileName, this.text)));
            }
            if ("]".contains(String.valueOf(this.peek()))) {
                this.read();
                return new Result.Ok<>(new Token.CloseBracket(
                    new Span(findLine(this.index), findColumn(this.index), this.fileName, this.text)));
            }
            if (";".contains(String.valueOf(this.peek()))) {
                this.read();
                return new Result.Ok<>(new Token.Semicolon(
                    new Span(findLine(this.index), findColumn(this.index), this.fileName, this.text)));
            }
            if (":".contains(String.valueOf(this.peek()))) {
                this.read();
                return new Result.Ok<>(new Token.Colon(
                    new Span(findLine(this.index), findColumn(this.index), this.fileName, this.text)));
            }
            if (",".contains(String.valueOf(this.peek()))) {
                this.read();
                return new Result.Ok<>(new Token.Comma(
                    new Span(findLine(this.index), findColumn(this.index), this.fileName, this.text)));
            }

        } catch (StringIndexOutOfBoundsException _) {
        }
        return new Result.Err<>(null);
    }
}
