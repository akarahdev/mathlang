package net.realmofuz.runtime;

public sealed interface Instruction {
    record Number(
            String value
    ) implements Instruction {

        @Override
        public void evaluate(RuntimeContext ctx) {

        }
    }

    record Variable(
            String name
    ) implements Instruction {
        @Override
        public void evaluate(RuntimeContext ctx) {

        }
    }
    void evaluate(RuntimeContext ctx);
}
