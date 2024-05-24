package net.realmofuz.parser.ast.expr;

import net.realmofuz.codegen.CodegenContext;
import net.realmofuz.parser.ast.AST;

public record Exponent(
        AST.Expression lhs,
        AST.Expression rhs
) implements AST.Expression, AST {
    @Override
    public void codegen(CodegenContext codegenContext) {
        lhs.codegen(codegenContext);
        rhs.codegen(codegenContext);
        codegenContext.binaryOperation("pow");
    }
}
