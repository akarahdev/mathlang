package net.realmofuz.type;

import java.util.HashMap;

/**
 * Represents type data for a AST.Module
 * @param functionDatas Type data associated with each function
 */
public record ASTModuleTypeData(
    HashMap<String, FunctionTypeData> functionDatas
) {}
