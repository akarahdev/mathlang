package net.realmofuz.codegen;

import net.realmofuz.type.Type;

/**
 * Represents codegen data for a variable.
 * @param name Name of the variable in the code.
 * @param index Index of the variable in the locals stack.
 * @param type The type of the variable.
 */
public record VariableCodegenData(
    String name,
    int index,
    Type type
) {}
