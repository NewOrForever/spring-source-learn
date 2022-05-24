package org.example;

import static org.junit.Assert.assertTrue;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.junit.Test;

import java.util.*;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void testMytransaction() {
        MyTransactionCacheManager myTransactionCacheManager = new MyTransactionCacheManager();
        Cache cache = new PerpetualCache("test");
        MyTransactionCache transactionalCache = myTransactionCacheManager.getTransactionalCache(cache);
        System.out.print(transactionalCache);
    }

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
class MyTransactionCacheManager{
    private final Map<Cache, MyTransactionCache> map = new HashMap<>();

    public MyTransactionCache getTransactionalCache(Cache cache) {
        return map.computeIfAbsent(cache, MyTransactionCache::new);
    }

}
class MyTransactionCache{
    private Cache delegate;

    public MyTransactionCache(Cache cache) {
        this.delegate = cache;
    }
}
