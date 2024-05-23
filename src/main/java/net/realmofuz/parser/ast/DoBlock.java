package net.realmofuz.parser.ast;

import net.realmofuz.codegen.CodegenContext;

import java.util.List;

public record DoBlock(
        List<Expression> expressionList
) implements AST.Expression, AST {
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
