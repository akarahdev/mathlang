package net.realmofuz.codegen;

import net.realmofuz.type.Type;

public sealed interface CodegenError {
    record UnexpectedType(
        Type expected,
        Type found
    ) implements CodegenError {
        public String message() {
            return STR."expected type \{expected}, found \{found}";
        }
    }

    String message();
}
