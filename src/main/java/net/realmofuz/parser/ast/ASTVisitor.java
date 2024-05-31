package net.realmofuz.parser.ast;

import net.realmofuz.lexer.Span;

public interface ASTVisitor {
    void visitDo(AST.Block.DoBlock doBlock);
    void visitSwitch(AST.Block.SwitchBlock switchBlock);

    void visitUnaryOperation(AST.Expression.UnaryOperation operation);
    void visitBinaryOperation(AST.Expression.BinaryOperation operation);

    void visitNumber(AST.Value.NumberValue numberValue);
    void visitVariable(AST.Value.VariableValue variableValue);
    void visitLambda(AST.Value.FunctionValue functionValue);

    default void visitGeneric(AST ast) {
        switch (ast) {
            case AST.Block.DoBlock doBlock -> this.visitDo(doBlock);
            case AST.Block.SwitchBlock switchBlock -> this.visitSwitch(switchBlock);

            case AST.Expression.BinaryOperation operation -> this.visitBinaryOperation(operation);
            case AST.Expression.UnaryOperation operation -> this.visitUnaryOperation(operation);

            case AST.Value.NumberValue numberValue -> this.visitNumber(numberValue);
            case AST.Value.VariableValue variableValue -> this.visitVariable(variableValue);
            case AST.Value.FunctionValue functionValue -> this.visitLambda(functionValue);

            case AST.Value _, AST _ -> {}
        }
    }
}
