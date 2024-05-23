package net.realmofuz.parser.ast;

import net.realmofuz.codegen.CodegenContext;

import java.util.List;

public record Module(
        List<FunctionDeclaration> functionDeclarations
) implements AST {
    @Override
    public void codegen(CodegenContext codegenContext) {

    }
}
