package net.realmofuz.type;

import java.lang.constant.ClassDesc;

public sealed interface Type {
    record Integer() implements Type {
        @Override
        public ClassDesc classDesc() {
            return ClassDesc.of("net.realmofuz.runtime.Number");
        }
    }
    record Real() implements Type {
        @Override
        public ClassDesc classDesc() {
            return ClassDesc.of("net.realmofuz.runtime.Number");
        }
    }
    record Complex() implements Type {
        @Override
        public ClassDesc classDesc() {
            return ClassDesc.of("net.realmofuz.runtime.Number");
        }
    }

    ClassDesc classDesc();
}
