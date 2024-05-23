package net.realmofuz.type;

/**
 * Represents a type in the code.
 * Note that in the ClassDesc's, the type should be autoconverted to "net.realmofuz.runtime.RuntimeValue" so
 * it can operate using `Operations`. The compiler will do the rest of the checking.
 */
public sealed interface Type {
    record Integer() implements Type {
        @Override
        public int typeWeight() {
            return 1;
        }
    }
    record Real() implements Type {
        @Override
        public int typeWeight() {
            return 2;
        }
    }
    record Complex() implements Type {
        @Override
        public int typeWeight() {
            return 3;
        }
    }

    int typeWeight();
}
