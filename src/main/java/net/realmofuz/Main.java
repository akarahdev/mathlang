package net.realmofuz;

import net.realmofuz.lexer.Lexer;

public class Main {
    public static void main(String[] args) {
        System.out.println(
            Lexer.lex(
                "f(x)=5+10"
            )
        );

    }
}