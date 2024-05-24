package net.realmofuz.parser.ast.expr;

import net.realmofuz.codegen.CodegenContext;
import net.realmofuz.parser.ast.AST;

import java.util.List;

public record Parenthesis(
        List<AST.Expression> inner
) implements AST.Expression, AST {
    @Override
    public void codegen(CodegenContext codegenContext) {
        for(var e : inner)
            e.codegen(codegenContext);
    }
}