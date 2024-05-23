package net.realmofuz.parser.ast;

import net.realmofuz.codegen.CodegenContext;

import java.util.List;

public record Parenthesis(
        List<Expression> inner
) implements AST.Expression, AST {
    @Override
    public void codegen(CodegenContext codegenContext) {
        for(var e : inner)
            e.codegen(codegenContext);
    }
}