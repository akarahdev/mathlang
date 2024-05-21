package net.realmofuz.lexer;

import net.realmofuz.util.Result;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    public String text;
    public List<Token> tokenList = new ArrayList<>();
    int index = -1;

    public Lexer(String text) {
        this.text = text;
    }

    public static List<Token> lex(String input) {
        var lexer = new Lexer(input);

        return lexer.startLexing();
    }

    public char read() {
        do index++;
        while (Character.isWhitespace(this.text.charAt(index)));
        return this.text.charAt(index);
    }

    public char read(int ahead) {
        index += ahead;
        while (Character.isWhitespace(this.text.charAt(index)))
            index++;
        return this.text.charAt(index);
    }

    public char peek() {
        var f = index + 1;
        while (Character.isWhitespace(this.text.charAt(f)))
            f++;
        return this.text.charAt(f);
    }

    public char peek(int ahead) {
        var f = index + ahead;
        while (Character.isWhitespace(this.text.charAt(f)))
            f++;
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
            if(check.equals("->")) {
                System.out.println("ch: " + ch);
                System.out.println("cmp: " + peek(charIndex));
            }
            if(ch != peek(charIndex)) {
                return false;
            }
            charIndex++;
        }
        this.index += check.length();
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
                    return new Result.Ok<>(new Token.Keyword(kw));
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
                        sb.toString()));
            }

            var operatorString = "+-*/=^?";
            if (operatorString.contains(String.valueOf(this.peek()))) {
                var sb = new StringBuilder();
                while(operatorString.contains(String.valueOf(this.peek())))
                    sb.append(this.read());
                return new Result.Ok<>(new Token.Symbol(sb.toString()));
            }

            var identString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_";
            if (identString.contains(String.valueOf(this.peek()))) {
                var sb = new StringBuilder();
                while(identString.contains(String.valueOf(this.peek())))
                    sb.append(this.read());
                return new Result.Ok<>(new Token.Symbol(sb.toString()));
            }

            if ("(".contains(String.valueOf(this.peek()))) {
                this.read();
                return new Result.Ok<>(new Token.OpenParen());
            }
            if (")".contains(String.valueOf(this.peek()))) {
                this.read();
                return new Result.Ok<>(new Token.CloseParen());
            }
            if ("{".contains(String.valueOf(this.peek()))) {
                this.read();
                return new Result.Ok<>(new Token.OpenBrace());
            }
            if ("}".contains(String.valueOf(this.peek()))) {
                this.read();
                return new Result.Ok<>(new Token.CloseBrace());
            }
            if ("[".contains(String.valueOf(this.peek()))) {
                this.read();
                return new Result.Ok<>(new Token.OpenBracket());
            }
            if ("]".contains(String.valueOf(this.peek()))) {
                this.read();
                return new Result.Ok<>(new Token.CloseBracket());
            }
            if (";".contains(String.valueOf(this.peek()))) {
                this.read();
                return new Result.Ok<>(new Token.Semicolon());
            }
            if (":".contains(String.valueOf(this.peek()))) {
                this.read();
                return new Result.Ok<>(new Token.Colon());
            }

        } catch (StringIndexOutOfBoundsException _) {
        }
        return new Result.Err<>(null);
    }
}
