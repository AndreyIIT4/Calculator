package test;



import junit.framework.TestCase;

import org.junit.Test;

import by.epam.calculator.calc.CalcOperations;




public class ExampleUnitTest {
    @Test
    public void add() throws Exception {
        CalcOperations operations = new CalcOperations();
        int result = (int) operations.add(2,2);
        int expected = 4;
        TestCase.assertEquals(expected, result);
    }
    @Test
    public void subtract() throws Exception {
        CalcOperations operations = new CalcOperations();
        int result = (int) operations.subtract(4,2);
        int expected = 2;
        TestCase.assertEquals(expected, result);
    }
    @Test
    public void multiply() throws Exception {
        CalcOperations operations = new CalcOperations();
        int result = (int) operations.multiply(4,2);
        int expected = 8;
        TestCase.assertEquals(expected, result);
    }
    @Test
    public void divide() throws Exception {
        CalcOperations operations = new CalcOperations();
        int result = (int) operations.divide(8,2);
        int expected = 4;
        TestCase.assertEquals(expected, result);
    }

}
