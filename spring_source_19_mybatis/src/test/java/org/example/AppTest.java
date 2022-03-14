package org.example;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void TestDoWhileGetInterfaces() {
        Class<?> a = A.class;
        Set<Class<?>> interfaceSet = new HashSet<>();
        do {
            Collections.addAll(interfaceSet, a.getInterfaces());
            a = a.getSuperclass();
        } while (a != null);

        Class[] classes = interfaceSet.toArray(new Class[interfaceSet.size()]);
        for (Class aClass : classes) {
            System.out.println(aClass);
        }
    }

    public class A extends B implements C {

    }

    public class B implements D {

    }

    public interface C {
    }

    public interface D {
    }
}
