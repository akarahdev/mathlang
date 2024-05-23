package net.realmofuz.parser.ast;

import net.realmofuz.codegen.CodegenContext;

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