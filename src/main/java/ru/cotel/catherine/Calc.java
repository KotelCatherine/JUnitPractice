package ru.cotel.catherine;

import org.junit.Test;

public class Calc {
    public int add(int a, int b){

        if (b==0){
            throw new ArithmeticException("Devid 0");
        }

        return a/b;
    }
}
