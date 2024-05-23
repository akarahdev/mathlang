package net.realmofuz.parser.ast;

import net.realmofuz.codegen.CodegenContext;

public record Subtraction(
        AST.Expression lhs,
        AST.Expression rhs
) implements AST.Expression, AST {
    @Override
    public void codegen(CodegenContext codegenContext) {
        lhs.codegen(codegenContext);
        rhs.codegen(codegenContext);
        codegenContext.binaryOperation("sub");
    }
}