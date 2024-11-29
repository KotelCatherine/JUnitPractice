package ru.cotel.catherine;


import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class TestApp {


    @Test()
    public void add() {
        Calc calc = new Calc();
     //   assertEquals(6, calc.add(2, 4));
        assertThrows(ArithmeticException.class, ()->calc.add(5,0));
    }
}
