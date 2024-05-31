package net.realmofuz;

import net.realmofuz.lexer.Lexer;
import net.realmofuz.parser.Parser;
import net.realmofuz.runtime.FunctionFinder;
import net.realmofuz.util.ASTPrinter;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {

        var tokens = Lexer.lex(Files.readString(Path.of(args[0])), args[0]);

        System.out.println(tokens);

        var p = new Parser(tokens);
        var tree = p.parse();

        System.out.println(tree);

        System.out.println("------");
        for(var t : tree) {
            t.accept(new ASTPrinter());
            System.out.println("------");
        }

        var functions = FunctionFinder.findFunctions(tree);
        System.out.println(functions);
    }
}