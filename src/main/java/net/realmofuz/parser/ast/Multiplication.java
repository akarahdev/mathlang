package net.realmofuz.parser.ast;

import net.realmofuz.codegen.CodegenContext;

import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;

public record Multiplication(
        AST.Expression lhs,
        AST.Expression rhs
) implements AST.Expression, AST {
    @Override
    public void codegen(CodegenContext codegenContext) {
        var cb = codegenContext.codeBuilder();
        if(lhs instanceof VariableValue lv
                && rhs instanceof Parenthesis pr) {
            pr.codegen(codegenContext);
            cb.invokestatic(
                    ClassDesc.of("net.realmofuz.Runtime"),
                    lv.varName(),
                    MethodTypeDesc.of(
                            ClassDesc.of("net.realmofuz.runtime.RuntimeValue"),
                            codegenContext.typeData().functionDatas().get(lv.varName()).parameterTypes().stream().map(it ->
                                    ClassDesc.of("net.realmofuz.runtime.RuntimeValue")).toList()
                    )
            );
            return;
        }
        lhs.codegen(codegenContext);
        rhs.codegen(codegenContext);
        codegenContext.binaryOperation("mul");
    }
}
