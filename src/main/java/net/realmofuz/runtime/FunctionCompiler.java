package net.realmofuz.runtime;

import net.realmofuz.parser.ast.AST;
import net.realmofuz.parser.ast.ASTVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FunctionCompiler implements ASTVisitor {
    ArrayList<Instruction> instructions = new ArrayList<>(;)
    public static List<Instruction> compile(AST.Value.FunctionValue fv, HashMap<Integer, AST.Value.FunctionValue> fvs) {
        var fc = new FunctionCompiler();
        fv.accept(fc);
        return fc.instructions;
    }

    @Override
    public void visitDo(AST.Block.DoBlock doBlock) {

    }

    @Override
    public void visitSwitch(AST.Block.SwitchBlock switchBlock) {

    }

    @Override
    public void visitUnaryOperation(AST.Expression.UnaryOperation operation) {

    }

    @Override
    public void visitBinaryOperation(AST.Expression.BinaryOperation operation) {

    }

    @Override
    public void visitNumber(AST.Value.NumberValue numberValue) {

    }

    @Override
    public void visitVariable(AST.Value.VariableValue variableValue) {

    }

    @Override
    public void visitLambda(AST.Value.FunctionValue functionValue) {

    }
}
