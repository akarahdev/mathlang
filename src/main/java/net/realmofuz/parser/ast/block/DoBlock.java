package net.realmofuz.parser.ast.block;

import net.realmofuz.codegen.CodegenContext;
import net.realmofuz.parser.ast.AST;

import java.util.List;

public record DoBlock(
        List<AST.Expression> expressionList
) implements AST.Expression, AST, AST.Block {
    @Override
    public void codegen(CodegenContext codegenContext) {
        var i = 0;
        for(var expr : expressionList) {
            expr.codegen(codegenContext);
            i++;
            if(i != expressionList.size())
                codegenContext.codeBuilder().pop();
        }
        codegenContext.codeBuilder().areturn();
    }
}
