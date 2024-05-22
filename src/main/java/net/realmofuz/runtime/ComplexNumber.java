package net.realmofuz.runtime;

public class ComplexNumber implements RuntimeValue {
    double real = 0.0;
    double imag = 0.0;

    public ComplexNumber(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    public ComplexNumber(double real) {
        this.real = real;
        this.imag = 0.0;
    }
}
