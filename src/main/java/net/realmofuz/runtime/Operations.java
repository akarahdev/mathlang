package net.realmofuz.runtime;

import java.math.BigDecimal;

public class Operations {
    public static Number number(
        String real,
        String imag
    ) {
        return new Number(
            new BigDecimal(real),
            new BigDecimal(imag)
        );
    }
    public static RuntimeValue add(
            RuntimeValue lhs,
            RuntimeValue rhs
    ) {
        if(lhs instanceof Number ln
        && rhs instanceof Number rn) {
            return new Number(
                    ln.real.add(rn.real),
                    ln.imag.add(rn.imag)
            );
        }
        return null;
    }

    public static RuntimeValue sub(
            RuntimeValue lhs,
            RuntimeValue rhs
    ) {
        if(lhs instanceof Number ln
                && rhs instanceof Number rn) {
            return new Number(
                    ln.real.subtract(rn.real),
                    ln.imag.subtract(rn.imag)
            );
        }
        return null;
    }

    public static RuntimeValue mul(
            RuntimeValue lhs,
            RuntimeValue rhs
    ) {
        if(lhs instanceof Number ln
                && rhs instanceof Number rn) {
            return new Number(
                    (ln.real.multiply(rn.real)).add(ln.imag.multiply(rn.real)),
                    (ln.real.multiply(rn.imag)).add(ln.imag.multiply(rn.imag))
            );
        }
        return null;
    }

    public static RuntimeValue div(
            RuntimeValue lhs,
            RuntimeValue rhs
    ) {
        if(lhs instanceof Number ln
                && rhs instanceof Number rn) {
            return new Number(
                    ln.real.subtract(rn.real),
                    ln.imag.subtract(rn.imag)
            );
        }
        return null;
    }

    public static RuntimeValue sin(
            RuntimeValue value
    ) {
        if(value instanceof Number n) {

        }
        return null;
    }
}
