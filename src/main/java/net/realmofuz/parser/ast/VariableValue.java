package net.realmofuz.parser.ast;

import net.realmofuz.codegen.CodegenContext;

public record VariableValue(
        String varName
) implements AST.Expression, AST {
    @Override
    public void codegen(CodegenContext codegenContext) {
        codegenContext.codeBuilder().aload(codegenContext.variables().get(varName).index());
    }
}