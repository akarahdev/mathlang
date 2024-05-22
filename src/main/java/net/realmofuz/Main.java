package net.realmofuz;

import net.realmofuz.codegen.CodegenContext;
import net.realmofuz.lexer.Lexer;
import net.realmofuz.parser.Parser;
import net.realmofuz.runtime.ByteClassLoader;
import net.realmofuz.runtime.Number;
import net.realmofuz.type.TypeGatherer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Arrays;

public class Main {
    public static void main(String[] args)
        throws NoSuchMethodException, InvocationTargetException,
        IllegalAccessException, IOException {
        var tokens = Lexer.lex(
            """
                main() -> C = do {
                    f(3, 6);
                }
                
                
                f(x: C, y: C) -> C = x/y;
                """
        );
        System.out.println(tokens);

        var p = new Parser(tokens);
        var tree = p.parse();
        System.out.println(tree);


        var typeData = new TypeGatherer().gather(tree);

        System.out.println(STR."TypeData: \{typeData}");
        var bytes = CodegenContext.compileModule(tree, typeData);
        System.out.println(Arrays.toString(bytes));

        var c = new ByteClassLoader().findClass("net.realmofuz.Runtime", bytes);
        Files.write(Path.of("./target/classes/net/realmofuz/Runtime.class"), bytes);
        var r = c.getDeclaredMethod("main").invoke(null);
        System.out.println(r);
    }

    public static void test() {
        new Number(
            new BigDecimal("1")
        );
    }
}