package org.example;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private final Map map = new HashMap<>();

    public static void main(String[] args) {
//        AppTest appTest = new AppTest();
//        AppTest appTest1 = new AppTest();
//        System.out.println();

        Object a = new Object();
        Object b = a;
        b = new Object();
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}
