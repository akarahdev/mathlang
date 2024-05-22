package net.realmofuz.parser.ast;

import net.realmofuz.codegen.CodegenContext;
import net.realmofuz.type.Type;

import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.util.List;

@SuppressWarnings("preview")
public sealed interface AST {
    sealed interface Expression {
        void codegen(CodegenContext cc);

        record NumberValue(
            String number
        ) implements AST.Expression, AST {
            @Override
            public void codegen(CodegenContext codegenContext) {
                codegenContext.loadNumber(
                    number,
                    "0"
                );
            }
        }

        record VariableValue(
            String varName
        ) implements AST.Expression, AST {
            @Override
            public void codegen(CodegenContext codegenContext) {
                codegenContext.codeBuilder().aload(codegenContext.variables().get(varName).index());
            }
        }

        record Exponent(
            AST.Expression lhs,
            AST.Expression rhs
        ) implements AST.Expression, AST {
            @Override
            public void codegen(CodegenContext codegenContext) {
                lhs.codegen(codegenContext);
                rhs.codegen(codegenContext);
                codegenContext.binaryOperation("pow");
            }
        }

        record Multiplication(
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
                        lv.varName,
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

        record Division(
            AST.Expression lhs,
            AST.Expression rhs
        ) implements AST.Expression, AST {
            @Override
            public void codegen(CodegenContext codegenContext) {
                lhs.codegen(codegenContext);
                rhs.codegen(codegenContext);
                codegenContext.binaryOperation("div");
            }
        }

        record Addition(
            AST.Expression lhs,
            AST.Expression rhs
        ) implements AST.Expression, AST {
            @Override
            public void codegen(CodegenContext codegenContext) {
                lhs.codegen(codegenContext);
                rhs.codegen(codegenContext);
                codegenContext.binaryOperation("add");
            }
        }

        record Subtraction(
            AST.Expression lhs,
            AST.Expression rhs
        ) implements AST.Expression, AST {
            @Override
            public void codegen(CodegenContext codegenContext) {
                lhs.codegen(codegenContext);
                rhs.codegen(codegenContext);
                codegenContext.binaryOperation("sub");
            }
        }

        record DoBlock(
            List<Expression> expressionList
        ) implements AST.Expression, AST {
            @Override
            public void codegen(CodegenContext codegenContext) {
                var i = 0;
                for(var expr : expressionList) {
                    expr.codegen(codegenContext);
                    i++;
                    if(i != expressionList.size())
                        codegenContext.codeBuilder().pop();
                }
                codegenContext.codeBuilder().areturn();
            }
        }

        record SwitchBlock(
            List<Expression> expressionList
        ) implements AST.Expression, AST {
            @Override
            public void codegen(CodegenContext codegenContext) {

            }
        }

        record Parenthesis(
                List<Expression> inner
        ) implements AST.Expression, AST {
            @Override
            public void codegen(CodegenContext codegenContext) {
                for(var e : inner)
                    e.codegen(codegenContext);
            }
        }
    }

    record FunctionDeclaration(
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

    record Module(
        List<FunctionDeclaration> functionDeclarations
    ) implements AST {
        @Override
        public void codegen(CodegenContext codegenContext) {

        }
    }

    void codegen(CodegenContext codegenContext);


}
