package net.realmofuz.type;

/**
 * Represents a type in the code.
 * Note that in the ClassDesc's, the type should be autoconverted to "net.realmofuz.runtime.RuntimeValue" so
 * it can operate using `Operations`. The compiler will do the rest of the checking.
 */
public sealed interface Type {
    record Integer() implements Type { }
    record Real() implements Type { }
    record Complex() implements Type { }
}
