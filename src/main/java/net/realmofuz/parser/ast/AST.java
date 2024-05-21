package net.realmofuz.parser.ast;

import net.realmofuz.type.Type;

import java.util.List;

public sealed interface AST {
    sealed interface Expression {
        record NumberValue(
            String number
        ) implements AST.Expression, AST {}

        record Exponent(
            AST.Expression lhs,
            AST.Expression rhs
        ) implements AST.Expression, AST {}

        record Multiplication(
            AST.Expression lhs,
            AST.Expression rhs
        ) implements AST.Expression, AST {}

        record Division(
            AST.Expression lhs,
            AST.Expression rhs
        ) implements AST.Expression, AST {}

        record Addition(
            AST.Expression lhs,
            AST.Expression rhs
        ) implements AST.Expression, AST {}

        record Subtraction(
            AST.Expression lhs,
            AST.Expression rhs
        ) implements AST.Expression, AST {}

        record DoBlock(
            List<Expression> expressionList
        ) implements AST.Expression, AST {}

        record SwitchBlock(
            List<Expression> expressionList
        ) implements AST.Expression, AST {}
    }
    record FunctionDeclaration(
            String functionName,
            Type functionReturnType,
            Expression expression
    ) implements AST {}

    record Module() implements AST {}
}
