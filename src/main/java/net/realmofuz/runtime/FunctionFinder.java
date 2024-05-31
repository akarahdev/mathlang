package net.realmofuz.runtime;

import net.realmofuz.parser.ast.AST;
import net.realmofuz.parser.ast.ASTVisitor;

import java.util.HashMap;
import java.util.List;

public class FunctionFinder implements ASTVisitor {
    public HashMap<Integer, AST.Value.FunctionValue> fvs = new HashMap<>();

    public static HashMap<Integer, AST.Value.FunctionValue> findFunctions(List<AST.Expression> exprs) {
        var ff = new FunctionFinder();
        for(var e : exprs)
            e.accept(ff);
        return ff.fvs;
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
        fvs.put(functionValue.hashCode(), functionValue);
    }
}
