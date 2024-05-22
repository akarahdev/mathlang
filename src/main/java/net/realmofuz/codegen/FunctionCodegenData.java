package net.realmofuz.codegen;

import net.realmofuz.type.FunctionTypeData;

import java.lang.classfile.MethodBuilder;

/**
 * Codegen data associated with the function.
 * @param methodBuilder The methodbuilder associated with the function.
 * @param functionTypeData A reference of the function's type data.
 */
public record FunctionCodegenData(
    MethodBuilder methodBuilder,
    FunctionTypeData functionTypeData
) {}
