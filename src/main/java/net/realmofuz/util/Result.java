package net.realmofuz.util;

public sealed interface Result<T, E> {
    record Ok<T, E>(T value) implements Result<T, E> {
        @Override
        public T unwrap() {
            return this.value();
        }

        @Override
        public E unwrapErr() {
            throw new BadUnwrap();
        }

        @Override
        public boolean isOk() {
            return true;
        }
    }

    record Err<T, E>(E value) implements Result<T, E> {
        @Override
        public T unwrap() {
            throw new BadUnwrap();
        }

        @Override
        public E unwrapErr() {
            return this.value();
        }

        @Override
        public boolean isOk() {
            return false;
        }
    }

    T unwrap();
    E unwrapErr();
    boolean isOk();
}
