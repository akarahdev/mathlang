package net.realmofuz.parser.ast;

import net.realmofuz.codegen.CodegenContext;
import net.realmofuz.type.Type;

import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.util.List;

@SuppressWarnings("preview")
public interface AST {
    interface Expression extends AST { }




    /**
     * Generates the code responsible for the execution of
     * this AST node. Does not apply to `AST.Module` or `AST.FunctionDeclaration`
     * due to them having special cases.
     * @param codegenContext Context for the code generation
     */
    void codegen(CodegenContext codegenContext);


}
