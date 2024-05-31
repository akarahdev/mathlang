package net.realmofuz.util;

import net.realmofuz.parser.ast.AST;
import net.realmofuz.parser.ast.ASTVisitor;

public class ASTPrinter implements ASTVisitor {
    @Override
    public void visitDo(AST.Block.DoBlock doBlock) {
        System.out.println("Do Block: " + doBlock.hashCode());
    }

    @Override
    public void visitSwitch(AST.Block.SwitchBlock switchBlock) {
        System.out.println("Switch Block: " + switchBlock.hashCode());
    }

    @Override
    public void visitUnaryOperation(AST.Expression.UnaryOperation operation) {
        System.out.println(STR."Unary Operation: \{operation.type()} \{operation.hashCode()}");
    }

    @Override
    public void visitBinaryOperation(AST.Expression.BinaryOperation operation) {
        System.out.println(STR."Binary Operation: \{operation.operationType()} \{operation.hashCode()}");
    }

    @Override
    public void visitNumber(AST.Value.NumberValue numberValue) {
        System.out.println("Number: " + numberValue.number() + " " + numberValue.hashCode());
    }

    @Override
    public void visitVariable(AST.Value.VariableValue variableValue) {
        System.out.println("Variable: " + variableValue.name()  + " " + variableValue.hashCode());
    }

    @Override
    public void visitLambda(AST.Value.FunctionValue functionValue) {
        System.out.println("Lambda: " + functionValue.hashCode());
    }
}
