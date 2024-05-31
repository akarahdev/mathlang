package net.realmofuz.parser.ast;

import net.realmofuz.type.Type;

import java.util.HashMap;
import java.util.List;

@SuppressWarnings("preview")
public sealed interface AST {
    sealed interface Value extends AST.Expression {
        record Parenthesis(
                List<AST.Expression> exprs
        ) implements Value {
            @Override
            public void accept(ASTVisitor visitor) {
                for(var e : exprs)
                    e.accept(visitor);
            }
        }

        record NumberValue(
                String number
        ) implements Value {
            @Override
            public void accept(ASTVisitor visitor) {
                visitor.visitNumber(this);
            }
        }

        record VariableValue(
                String name
        ) implements Value {
            @Override
            public void accept(ASTVisitor visitor) {
                visitor.visitVariable(this);
            }
        }

        record FunctionValue(
                List<AST.Expression> params,
                AST.Expression expression
        ) implements Value {
            @Override
            public void accept(ASTVisitor visitor) {
                visitor.visitLambda(this);
                for(var p : params)
                    p.accept(visitor);
                expression.accept(visitor);

            }
        }
    }

    sealed interface Expression extends AST {
        record BinaryOperation(
                AST.Expression lhs,
                AST.Expression rhs,
                BinaryOperationType operationType
        ) implements AST.Expression {
            @Override
            public void accept(ASTVisitor visitor) {
                lhs.accept(visitor);
                rhs.accept(visitor);
                visitor.visitBinaryOperation(this);
            }
        }

        record UnaryOperation(
                AST.Expression expr,
                UnaryOperationType type
        ) implements AST.Expression {
            @Override
            public void accept(ASTVisitor visitor) {
                expr.accept(visitor);
                visitor.visitUnaryOperation(this);
            }
        }
    }
//    sealed interface Header extends AST {
//
//    }
    sealed interface Block extends AST.Value {
        record DoBlock(
                List<AST.Expression> expressionList
        ) implements AST.Block {
            @Override
            public void accept(ASTVisitor visitor) {

            }
        }

        record SwitchBlock(
                List<AST.Expression> expressionList
        ) implements AST.Block {
            @Override
            public void accept(ASTVisitor visitor) {

            }
        }

    }


    /**
     * An AST visitor visits this node.
     */
    void accept(ASTVisitor visitor);


}
