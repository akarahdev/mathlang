package net.realmofuz.runtime;

import java.math.BigDecimal;

public record Number(
        BigDecimal real,
        BigDecimal imag
) implements RuntimeValue {

    @Override
    public RuntimeValue add(RuntimeValue other) {
        if (other instanceof Number rv)
            return new Number(
                    this.real.add(rv.real()),
                    this.imag.add(rv.imag())
            );
        return null;
    }

    @Override
    public RuntimeValue sub(RuntimeValue other) {
        if (other instanceof Number rv)
            return new Number(
                    this.real.subtract(rv.real()),
                    this.imag.subtract(rv.imag())
            );
        return null;
    }
}
