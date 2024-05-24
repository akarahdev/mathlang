package net.realmofuz.codegen;

import net.realmofuz.parser.ast.block.DoBlock;
import net.realmofuz.parser.ast.headers.Module;
import net.realmofuz.parser.ast.block.SwitchBlock;
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
import java.util.function.Consumer;

/**
 * Context for code generation.
 */
@SuppressWarnings("preview")
public class CodegenContext {
    /**
     * The builder for the current class being built.
     */
    ClassBuilder classBuilder;
    /**
     * The table of method names and their associated data.
     */
    HashMap<String, FunctionCodegenData> methods = new HashMap<>();
    /**
     * The name of the method currently being built.
     */
    String currentMethod;
    /**
     * The CodeBuilder building the current method.
     */
    CodeBuilder currentCodeBuilder;

    /**
     * Type Data for the AST.Module being built, gathered by TypeGatherer.
     */
    ASTModuleTypeData typeData;

    /**
     * A table of variable names and their associated data.
     */
    HashMap<String, VariableCodegenData> variables = new HashMap<>();

    /**
     * The current index for the JVM locals stack.
     */
    int variableIndex = 0;

    /**
     * Compile an AST.Module.
     * @param module The module to compile
     * @param typeData The type data for the module, gathered with {@link net.realmofuz.type.TypeGatherer}.
     * @return The bytes for the generated class
     */
    public static byte[] compileModule(
        Module module,
        ASTModuleTypeData typeData
    ) {
        var cc = new CodegenContext();
        cc.typeData = typeData;
        return ClassFile.of().build(
            ClassDesc.of("net.realmofuz.Runtime"),
            classBuilder -> {
                // Set up the classbuilder data
                cc.classBuilder = classBuilder;
                classBuilder.withFlags(ClassFile.ACC_PUBLIC);

                // Go through each function in the module and create
                // the associated function for it.
                for(var function : module.functionDeclarations()) {
                    // Set up current method
                    cc.currentMethod = function.functionName();

                    // Create the function
                    cc.createFunction(
                        function.functionName(),
                        function.parameters(),
                        function.parameterNames(),
                        function.functionReturnType(),
                        codeBuilder -> {
                            // Sets up codebuilder
                            cc.currentCodeBuilder = codeBuilder;

                            // This makes the function execute the expression you give it.
                            // `areturn` is inserted at the end because if you put in an expression
                            // such as:
                            // f(x) = x+3(2)
                            //        ^^^^^^
                            // The compiler won't know to add in the return unless you add the
                            // special case here.
                            function.expression().codegen(cc);
                            if(!(function.expression() instanceof DoBlock
                                || function.expression() instanceof SwitchBlock
                                ))
                                cc.codeBuilder().areturn();
                        }
                    );
                }
            }
        );
    }

    /**
     * Creates a function in the runtime.
     * @param name Name of the function
     * @param parameterTypes List of types for each parameter.
     * @param parameterNames List of names for each parameter.
     * @param returnType The type that this function returns.
     * @param code The lambda to execute with the CodeBuilder it generates. This codebuilder is automatically applied
     *             to the generated method.
     */
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

    /**
     * Loads a runtime Number onto the stack.
     * @param real The real part of the number.
     *             Follows the same rules as {@link java.math.BigDecimal#BigDecimal(String)}
     * @param imag The imaginary part of the number.
     *             Follows the same rules as {@link java.math.BigDecimal#BigDecimal(String)}
     */
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

    /**
     * Executes a unary operation on the stack. Returns 1 runtime value.
     * Accepts 1 parameter of {@link net.realmofuz.runtime.RuntimeValue} type.
     * @param functionName The operation to call on the class {@link net.realmofuz.runtime.Operations}
     */
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

    /**
     * Executes a unary operation on the stack. Returns 1 runtime value.
     * Accepts 2 parameters of {@link net.realmofuz.runtime.RuntimeValue} type.
     * @param functionName The operation to call on the class {@link net.realmofuz.runtime.Operations}
     */
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

    /**
     * @return The methodbuilder for the current function.
     */
    public MethodBuilder methodBuilder() {
        return methods.get(currentMethod).methodBuilder();
    }

    /**
     * @return The current builder for the code of the current function.
     */
    public CodeBuilder codeBuilder() {
        return currentCodeBuilder;
    }

    /**
     * @return The current table of variables in the current function
     */
    public HashMap<String, VariableCodegenData> variables() {
        return this.variables;
    }

    /**
     * @return The type data for this associated module being built
     */
    public ASTModuleTypeData typeData() {
        return this.typeData;
    }
}
