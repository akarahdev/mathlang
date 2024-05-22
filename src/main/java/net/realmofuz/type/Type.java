package net.realmofuz.type;

import java.lang.constant.ClassDesc;

public sealed interface Type {
    record ComplexNumber() implements Type {
        @Override
        public ClassDesc classDesc() {
            return ClassDesc.of("Lnet/realmofuz/runtime/ComplexNumber;");
        }
    }

    ClassDesc classDesc();
}
