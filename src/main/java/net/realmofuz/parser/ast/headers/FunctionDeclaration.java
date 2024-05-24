package net.realmofuz.parser.ast.headers;

import net.realmofuz.codegen.CodegenContext;
import net.realmofuz.parser.ast.AST;
import net.realmofuz.type.Type;

import java.util.List;

public record FunctionDeclaration(
        String functionName,
        List<Type> parameters,
        List<String> parameterNames,
        Type functionReturnType,
        AST.Expression expression
) implements AST, AST.Header {
    @Override
    public void codegen(CodegenContext codegenContext) {

    }
}
