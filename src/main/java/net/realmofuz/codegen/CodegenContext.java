package net.realmofuz.codegen;

import net.realmofuz.parser.ast.AST;
import net.realmofuz.type.Type;

import java.lang.classfile.ClassBuilder;
import java.lang.classfile.ClassFile;
import java.lang.classfile.CodeBuilder;
import java.lang.classfile.MethodBuilder;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("preview")
public class CodegenContext {
    ClassFile classFile;
    ClassBuilder classBuilder;
    HashMap<String, MethodBuilder> methods = new HashMap<>();
    String currentMethod;
    CodeBuilder currentCodeBuilder;

    public static byte[] compileModule(
        AST.Module module
    ) {
        var cc = new CodegenContext();
        return ClassFile.of().build(
            ClassDesc.of("me/endistic/Runtime"),
            classBuilder -> {
                classBuilder.withFlags(ClassFile.ACC_PUBLIC);
                for(var function : module.functionDeclarations()) {
                    cc.currentMethod = function.functionName();
                    cc.createFunction(
                        function.functionName(),
                        List.of(),
                        function.functionReturnType(),
                        codeBuilder -> {
                            cc.currentCodeBuilder = codeBuilder;
                            function.expression().codegen(cc);
                            codeBuilder.return_();
                        }
                    );


                }
            }
        );
    }

    public void createFunction(
        String name,
        List<Type> parameter,
        Type returnType,
        Consumer<CodeBuilder> code
    ) {
        classBuilder.withMethod(
            name,
            MethodTypeDesc.of(returnType.classDesc()),
            ClassFile.ACC_PUBLIC,
            methodBuilder -> {
                methods.put(name, methodBuilder);
                methodBuilder.withCode(code);
            }
        );
    }

    public MethodBuilder methodBuilder() {
        return methods.get(currentMethod);
    }

    public CodeBuilder codeBuilder() {
        return currentCodeBuilder;
    }
}
