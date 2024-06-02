package org.example;

import org.junit.Test;

public class TestClass {

    @Test
    public void testMethod1() {
        System.out.println("Test 1 executed");
    }

    @Test
    public void testMethod2() {
        System.out.println("Test 2 executed");
    }

    @Test
    public void nonTestMethod() {
        System.out.println("Not a valid test method");
    }

    @Test
    public void parameterTestMethod() {
        System.out.println("Test with parameteres executed");
    }
}
