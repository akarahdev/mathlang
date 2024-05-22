package net.realmofuz.parser.ast;

import net.realmofuz.codegen.CodegenContext;
import net.realmofuz.runtime.ComplexNumber;
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
                mb.ldc(Double.parseDouble(number));
                mb.new_(ClassDesc.of("Lnet/realmofuz/runtime/ComplexNumber;"));
                mb.dup();
                mb.invokespecial(
                    ClassDesc.of("Lnet/realmofuz/runtime/ComplexNumber;"),
                    "<init>",
                    MethodTypeDesc.of(
                        ClassDesc.of("V"),
                        ClassDesc.of("D")
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

            }
        }

        record Division(
            AST.Expression lhs,
            AST.Expression rhs
        ) implements AST.Expression, AST {
            @Override
            public void codegen(CodegenContext codegenContext) {

            }
        }

        record Addition(
            AST.Expression lhs,
            AST.Expression rhs
        ) implements AST.Expression, AST {
            @Override
            public void codegen(CodegenContext codegenContext) {

            }
        }

        record Subtraction(
            AST.Expression lhs,
            AST.Expression rhs
        ) implements AST.Expression, AST {
            @Override
            public void codegen(CodegenContext codegenContext) {

            }
        }

        record DoBlock(
            List<Expression> expressionList
        ) implements AST.Expression, AST {
            @Override
            public void codegen(CodegenContext codegenContext) {

            }
        }

        record SwitchBlock(
            List<Expression> expressionList
        ) implements AST.Expression, AST {
            @Override
            public void codegen(CodegenContext codegenContext) {

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
