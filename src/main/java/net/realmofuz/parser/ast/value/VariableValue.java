package net.realmofuz.parser.ast.value;

import net.realmofuz.codegen.CodegenContext;
import net.realmofuz.parser.ast.AST;

public record VariableValue(
        String varName
) implements AST.Expression, AST {
    @Override
    public void codegen(CodegenContext codegenContext) {
        codegenContext.codeBuilder().aload(codegenContext.variables().get(varName).index());
    }
}