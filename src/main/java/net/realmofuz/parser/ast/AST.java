package net.realmofuz.parser.ast;

import net.realmofuz.type.Type;

public sealed interface AST {
    record FunctionDeclaration(
            String functionName,
            Type functionReturnType
    ) implements AST {}

    record Module() implements AST {}
}
