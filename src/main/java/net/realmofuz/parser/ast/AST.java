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
                var mb = codegenContext.codeBuilder();
                mb.new_(ClassDesc.of("net.realmofuz.runtime.Number"));
                mb.dup();

                mb.new_(ClassDesc.of("java.math.BigDecimal"));
                mb.dup();

                mb.ldc(number);
                mb.invokespecial(
                        ClassDesc.of("java.math.BigDecimal"),
                        "<init>",
                        MethodTypeDesc.of(
                                ClassDesc.ofDescriptor("V"),
                                ClassDesc.of("java.lang.String")
                        )
                );


                mb.invokespecial(
                    ClassDesc.of("net.realmofuz.runtime.Number"),
                    "<init>",
                    MethodTypeDesc.of(
                        ClassDesc.ofDescriptor("V"),
                        ClassDesc.of("java.math.BigDecimal")
                    )
                );
            }
        }

        record Exponent(
            AST.Expression lhs,
            AST.Expression rhs
        ) implements AST.Expression, AST {
            @Override
            public void codegen(CodegenContext codegenContext) {

            }
        }

        record Multiplication(
            AST.Expression lhs,
            AST.Expression rhs
        ) implements AST.Expression, AST {
            @Override
            public void codegen(CodegenContext codegenContext) {
                lhs.codegen(codegenContext);
                rhs.codegen(codegenContext);
                codegenContext.codeBuilder()
                        .invokestatic(
                                ClassDesc.of("net.realmofuz.runtime.Operations"),
                                "mul",
                                MethodTypeDesc.of(
                                        ClassDesc.of("net.realmofuz.runtime.RuntimeValue"),
                                        ClassDesc.of("net.realmofuz.runtime.RuntimeValue"),
                                        ClassDesc.of("net.realmofuz.runtime.RuntimeValue")
                                )
                        );
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
                codegenContext.codeBuilder()
                        .invokestatic(
                                ClassDesc.of("net.realmofuz.runtime.Operations"),
                                "div",
                                MethodTypeDesc.of(
                                        ClassDesc.of("net.realmofuz.runtime.RuntimeValue"),
                                        ClassDesc.of("net.realmofuz.runtime.RuntimeValue"),
                                        ClassDesc.of("net.realmofuz.runtime.RuntimeValue")
                                )
                        );
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
                codegenContext.codeBuilder()
                        .invokestatic(
                                ClassDesc.of("net.realmofuz.runtime.Operations"),
                                "add",
                                MethodTypeDesc.of(
                                        ClassDesc.of("net.realmofuz.runtime.RuntimeValue"),
                                        ClassDesc.of("net.realmofuz.runtime.RuntimeValue"),
                                        ClassDesc.of("net.realmofuz.runtime.RuntimeValue")
                                )
                        );
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
                codegenContext.codeBuilder()
                        .invokestatic(
                                ClassDesc.of("net.realmofuz.runtime.Operations"),
                                "sub",
                                MethodTypeDesc.of(
                                        ClassDesc.of("net.realmofuz.runtime.RuntimeValue"),
                                        ClassDesc.of("net.realmofuz.runtime.RuntimeValue"),
                                        ClassDesc.of("net.realmofuz.runtime.RuntimeValue")
                                )
                        );
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
                Expression inner
        ) implements AST.Expression, AST {
            @Override
            public void codegen(CodegenContext codegenContext) {
                inner.codegen(codegenContext);
            }
        }
    }
    record FunctionDeclaration(
            String functionName,
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
