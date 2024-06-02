package org.example;

import org.junit.Test;

public class TestClass {

    @Test
    public void testMethod1() {
        System.out.println("Metoda de test 1 a fost executată");
    }

    @Test
    public void testMethod2() {
        System.out.println("Metoda de test 2 a fost executată");
    }

    @Test
    public void nonTestMethod() {
        System.out.println("Aceasta metodă nu este metodă de test");
    }

    @Test
    public void parameterTestMethod() {
        System.out.println("Metoda parametrizată a fost executată");
    }
}
//PS C:\Users\cosmi\IdeaProjects\Lab_12\bin> java -cp ".;../lib/junit-4.13.2.jar;../lib/hamcrest-core-1.3.jar" org.example.ClassAnalyzer "C:\Users\cosmi\IdeaProjects\Lab_12\bin\org\example\TestClass.class"