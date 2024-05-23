package net.realmofuz.codegen;

import net.realmofuz.lexer.Token;
import net.realmofuz.type.Type;

public sealed interface CompileError {
    record UnexpectedToken(
        Token found,
        Class<?> expected
    ) implements CompileError {
        public String message() {
            return STR."expected token \{expected}, found \{found}";
        }
        public int errorCode() {
            return 1;
        }
    }

    record UnexpectedValue(
        Token found
    ) implements CompileError {
        public String message() {
            return STR."expected a valid value, found \{found}";
        }
        public int errorCode() {
            return 2;
        }
    }

    record UnexpectedType(
        Type expected,
        Type found
    ) implements CompileError {
        public String message() {
            return STR."expected type \{expected}, found \{found}";
        }
        public int errorCode() {
            return 2;
        }
    }


    String message();
    int errorCode();
}
