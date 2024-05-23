package net.realmofuz.parser.ast;

import net.realmofuz.codegen.CodegenContext;
import net.realmofuz.type.Type;

import java.util.List;

public record FunctionDeclaration(
        String functionName,
        List<Type> parameters,
        List<String> parameterNames,
        Type functionReturnType,
        Expression expression
) implements AST {
    @Override
    public void codegen(CodegenContext codegenContext) {

    }
}
