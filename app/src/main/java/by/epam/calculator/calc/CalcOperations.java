package by.epam.calculator.calc;

import by.epam.calculator.exceptions.DivisionByZeroException;

public class CalcOperations {
    public static double add(double a, double b) {
        return a + b;
    }

    public static double subtract(double a, double b) {
        return a - b;
    }

    public static double multiply(double a, double b) {
        return a * b;
    }

    public static double divide(double a, double b) {
        if (b == 0) {
            throw new DivisionByZeroException();
        }
        return a/b;
    }

}
