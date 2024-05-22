package net.realmofuz.codegen;

import net.realmofuz.parser.ast.AST;
import net.realmofuz.type.Type;
import net.realmofuz.type.ASTModuleTypeData;

import java.lang.classfile.ClassBuilder;
import java.lang.classfile.ClassFile;
import java.lang.classfile.CodeBuilder;
import java.lang.classfile.MethodBuilder;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@SuppressWarnings("preview")
public class CodegenContext {
    ClassBuilder classBuilder;
    HashMap<String, FunctionCodegenData> methods = new HashMap<>();
    String currentMethod;
    CodeBuilder currentCodeBuilder;

    ASTModuleTypeData typeData;

    HashMap<String, VariableCodegenData> variables = new HashMap<>();
    int variableIndex = 0;

    public static byte[] compileModule(
        AST.Module module,
        ASTModuleTypeData typeData
    ) {
        var cc = new CodegenContext();
        cc.typeData = typeData;
        return ClassFile.of().build(
            ClassDesc.of("net.realmofuz.Runtime"),
            classBuilder -> {
                cc.classBuilder = classBuilder;
                classBuilder.withFlags(ClassFile.ACC_PUBLIC);
                for(var function : module.functionDeclarations()) {
                    System.out.println("function: " + function.functionName());
                    cc.currentMethod = function.functionName();
                    cc.createFunction(
                        function.functionName(),
                        function.parameters(),
                        function.parameterNames(),
                        function.functionReturnType(),
                        codeBuilder -> {
                            cc.currentCodeBuilder = codeBuilder;
                            function.expression().codegen(cc);
                            if(!(function.expression() instanceof AST.Expression.DoBlock
                                || function.expression() instanceof AST.Expression.SwitchBlock
                                ))
                                cc.codeBuilder().areturn();
                        }
                    );
                }
            }
        );
    }

    public void createFunction(
        String name,
        List<Type> parameterTypes,
        List<String> parameterNames,
        Type returnType,
        Consumer<CodeBuilder> code
    ) {
        variables.clear();
        variableIndex = -1;

        for(var t : parameterNames) {
            variables.put(t, new VariableCodegenData(
                t,
                ++variableIndex,
                new Type.Complex()
            ));
        }

        System.out.println(variables);
        System.out.println(parameterNames);
        System.out.println(parameterTypes);

        classBuilder.withMethod(
            name,
            MethodTypeDesc.of(
                ClassDesc.of("net.realmofuz.runtime.RuntimeValue"),
                parameterTypes.stream().map(it -> ClassDesc.of("net.realmofuz.runtime.RuntimeValue")).toList()
            ),
            ClassFile.ACC_STATIC + ClassFile.ACC_PUBLIC,
            methodBuilder -> {
                methods.put(name, new FunctionCodegenData(
                    methodBuilder,
                    typeData.functionDatas().get(name)
                ));
                methodBuilder.withCode(code);
            }
        );
    }

    public void loadNumber(
        String real,
        String imag
    ) {
        codeBuilder().ldc(real);
        codeBuilder().ldc(imag);
        codeBuilder().invokestatic(
            ClassDesc.of("net.realmofuz.runtime.Operations"),
            "number",
            MethodTypeDesc.of(
                ClassDesc.of("net.realmofuz.runtime.Number"),
                ClassDesc.of("java.lang.String"),
                ClassDesc.of("java.lang.String")
            )
        );
    }

    public void unaryOperation(String functionName) {
        currentCodeBuilder.invokestatic(
            ClassDesc.of("net.realmofuz.runtime.Operations"),
            functionName,
            MethodTypeDesc.of(
                ClassDesc.of("net.realmofuz.runtime.RuntimeValue"),
                ClassDesc.of("net.realmofuz.runtime.RuntimeValue")
            )
        );
    }

    public void binaryOperation(String functionName) {
        currentCodeBuilder.invokestatic(
            ClassDesc.of("net.realmofuz.runtime.Operations"),
            functionName,
            MethodTypeDesc.of(
                ClassDesc.of("net.realmofuz.runtime.RuntimeValue"),
                ClassDesc.of("net.realmofuz.runtime.RuntimeValue"),
                ClassDesc.of("net.realmofuz.runtime.RuntimeValue")
            )
        );
    }

    public MethodBuilder methodBuilder() {
        return methods.get(currentMethod).methodBuilder();
    }

    public CodeBuilder codeBuilder() {
        return currentCodeBuilder;
    }

    public HashMap<String, VariableCodegenData> variables() {
        return this.variables;
    }

    public ASTModuleTypeData typeData() {
        return this.typeData;
    }
}
