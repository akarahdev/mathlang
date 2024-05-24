package net.realmofuz.parser.ast.headers;

import net.realmofuz.codegen.CodegenContext;
import net.realmofuz.parser.ast.AST;

import java.util.List;

public record Module(
        List<FunctionDeclaration> functionDeclarations
) implements AST, AST.Header {
    @Override
    public void codegen(CodegenContext codegenContext) {

    }
}
