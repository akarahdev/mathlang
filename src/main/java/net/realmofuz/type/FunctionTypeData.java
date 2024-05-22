package net.realmofuz.type;

import java.util.HashMap;
import java.util.List;

/**
 * Represents the type data for a function
 * @param name Name of the function
 * @param returnType Return type of the function
 * @param parameterTypes Parameter types to call the function
 * @param parameterNames Names of the parameters, same order as `parameterTypes`
 * @param variableTypes Types of each variable and it's name in the function
 */
public record FunctionTypeData(
    String name,
    Type returnType,
    List<Type> parameterTypes,
    List<String> parameterNames,
    HashMap<String, Type> variableTypes
) {}
