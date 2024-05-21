package net.realmofuz;

import net.realmofuz.lexer.Lexer;
import net.realmofuz.parser.Parser;

public class Main {
    public static void main(String[] args) {
        var tokens = Lexer.lex(
                """
            main()-> C = do{
                1+2;
            }
            """
        );
        System.out.println(tokens);

        var p = new Parser(tokens);
        var t = p.parse();
        System.out.println(t);

    }
}