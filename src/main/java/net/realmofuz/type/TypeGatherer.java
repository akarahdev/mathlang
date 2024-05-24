package net.realmofuz.type;

import net.realmofuz.parser.ast.headers.Module;

import java.util.HashMap;

/**
 * A class responsible for gathering type information
 */
public class TypeGatherer {
    ASTModuleTypeData typeData;

    public ASTModuleTypeData gather(Module module) {
        typeData = new ASTModuleTypeData(
            new HashMap<>()
        );
        for(var func : module.functionDeclarations()) {
            typeData.functionDatas().put(func.functionName(), new FunctionTypeData(
                func.functionName(),
                func.functionReturnType(),
                func.parameters(),
                func.parameterNames(),
                new HashMap<>()
            ));
        }
        return typeData;
    }
}
