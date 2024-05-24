package net.realmofuz.parser.ast.value;

import net.realmofuz.codegen.CodegenContext;
import net.realmofuz.parser.ast.AST;

public record NumberValue(
        String number
) implements AST.Expression, AST {
    @Override
    public void codegen(CodegenContext codegenContext) {
        codegenContext.loadNumber(
                number,
                "0"
        );
    }
}