package net.realmofuz.parser.ast.block;

import net.realmofuz.codegen.CodegenContext;
import net.realmofuz.parser.ast.AST;

import java.util.List;

public record SwitchBlock(
        List<AST.Expression> expressionList
) implements AST.Expression, AST, AST.Block {
    @Override
    public void codegen(CodegenContext codegenContext) {

    }
}