package net.realmofuz.parser.ast;

import net.realmofuz.codegen.CodegenContext;

import java.util.List;

public record SwitchBlock(
        List<Expression> expressionList
) implements AST.Expression, AST {
    @Override
    public void codegen(CodegenContext codegenContext) {

    }
}