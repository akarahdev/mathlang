package net.realmofuz;

import net.realmofuz.codegen.CodegenContext;
import net.realmofuz.lexer.Lexer;
import net.realmofuz.parser.Parser;
import net.realmofuz.runtime.ByteClassLoader;
import net.realmofuz.runtime.Number;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var tokens = Lexer.lex(
                """
            main() -> C = do{
                4(3);
            }
            """
        );
        System.out.println(tokens);

        var p = new Parser(tokens);
        var t = p.parse();
        System.out.println(t);

        var bytes = CodegenContext.compileModule(t);
        System.out.println(Arrays.toString(bytes));

        var c = new ByteClassLoader().findClass("net.realmofuz.Runtime", bytes);
        var r = c.getDeclaredMethod("main").invoke(null);
        System.out.println(r);
    }

    public static void test() {
        new Number(
                new BigDecimal("1")
        );
    }
}