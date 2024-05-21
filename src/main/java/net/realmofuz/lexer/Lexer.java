package net.realmofuz.lexer;

import net.realmofuz.util.Result;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    public String text;
    int index = 0;
    public List<Token> tokenList = new ArrayList<>();

    public Lexer(String text) {
        this.text = text;
    }

    public char read() {
        return this.text.charAt(++index);
    }

    public char read(int ahead) {
        index += ahead;
        return this.text.charAt(index);
    }

    public char peek() {
        return this.text.charAt(index+1);
    }

    public char peek(int ahead) {
        return this.text.charAt(index+ahead);
    }

    public boolean stringAhead(String check) {
        var charIndex = 1;
        for(charIndex = 1; charIndex<check.length(); charIndex++) {
            var ch = check.charAt(charIndex);
            if(ch == peek(charIndex)) {
                charIndex++;
            } else return false;
        }
        read(charIndex);
        return true;
    }

    public static List<Token> lex(String input) {
        var lexer = new Lexer(input);

        return lexer.startLexing();
    }

    public List<Token> startLexing() {
        while(true) {
            var token = parseToken();
            if(token.isOk()) {
                this.tokenList.add(token.unwrap());
            } else {
                break;
            }
        }
        return this.tokenList;
    }

    public Result<Token, Void> parseToken() {
        try {
            var keywords = new String[]{
                "struct",
                "if",
                "supports",

                "do",
                "switch",

                "sqrt",
                "sin",
                "cos",
                "tan"
            };
            for(var kw : keywords) {
                if(stringAhead(kw)) {
                    return new Result.Ok<>(new Token.Symbol(kw));
                }
            }

            if("+-*/=?ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".contains(String.valueOf(this.peek()))) {
                return new Result.Ok<>(new Token.Symbol(
                    String.valueOf(this.read())));
            }
        } catch (StringIndexOutOfBoundsException _) {}


        return new Result.Err<>(null);
    }
}
