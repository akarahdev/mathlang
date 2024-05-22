package net.realmofuz.runtime;

import java.math.BigDecimal;

public class Number implements RuntimeValue {
    BigDecimal real;
    BigDecimal imag;
    BigDecimal j = new BigDecimal("0");
    BigDecimal k = new BigDecimal("0");

    public Number(BigDecimal real, BigDecimal imag) {
        this.real = real;
        this.imag = imag;
    }

    public Number(BigDecimal real) {
        this.real = real;
        this.imag = new BigDecimal(0);
    }

    public String toString() {
        return STR."\{this.real}+\{this.imag}i+\{this.j}j+\{this.k}k";
    }
}
