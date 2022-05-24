package org.example;

import static org.junit.Assert.assertTrue;

import com.mysql.jdbc.util.LRUCache;
import com.sun.org.apache.xerces.internal.impl.xs.opti.DefaultDocument;
import com.sun.xml.internal.txw2.Document;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.decorators.LruCache;
import org.apache.ibatis.cache.decorators.SynchronizedCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.io.Resources;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;

/**
 * Unit test for simple App.
 */
public class AppTest implements BridgeInterface<BridgeUser> {
    @Test
    public void testDate() {
        System.out.println(new Date());
        System.out.println(new Timestamp(System.currentTimeMillis()));
    }

    @Test
    public void testTryResource() throws IOException {

        Properties props = new Properties();

        // io流原本要try-catch-finally，finally中要去close资源，现在只需要try带参数的简易写法
        try (InputStream is = Resources.getResourceAsStream("mybatis.properties")) {
            // do something
            props.load(is);
        }

        Set<Map.Entry<Object, Object>> entries = props.entrySet();
        for (Map.Entry<Object, Object> entry : entries) {
            System.out.println(entry.getKey() + "->" + entry.getValue());
        }
    }

    @Test
    public void testUnmodifyList() {
        List<String> originList = new LinkedList<>();
        originList.add("a");
        originList.add("b");
        originList.add("c");
        /**
         * 用法：一个类维护了一个List属性（有get方法），拿到这个List后为了不让该list被修改只能是只读，所以让该get方法里面返回的是只读视图
         */

        // 返回的是originList的只读视图
        List<String> unmodList = Collections.unmodifiableList(originList);
        //unmodList.add("d"); // 会报错
    }

    @Test
    public void testMapnull() {
        Map<String, Object> map = new HashMap<>();
        map.put(null, "hahah");
    }

    @Test
    public void testMybatisCache() {
        // mybatis二级缓存的设计模式：装饰 + 责任链
        // 不断的向下委托，最终cache就包了很多层
        Cache cache = new PerpetualCache("id");
        cache = new LruCache(cache);
        cache = new SynchronizedCache(cache);
    }

    @Test
    public void testOptionalNullable() {
        Map variablesContext = new HashMap();
        Map configurationVariables = new HashMap();
        configurationVariables.put("a", "aa");

        // 如果configurationVariables不为空，则执行variablesContext.putAll(configurationVariables)
        Optional.ofNullable(configurationVariables).ifPresent(variablesContext::putAll);
    }

    @Test
    public void testBridgeMethod() throws NoSuchMethodException {
        // int a = 0;
        // assert a == 1;
        for (Method method : AppTest.class.getDeclaredMethods()) {
            if ("findBridge".equals(method.getName())) {
                System.out.println(method.isBridge());
            }
        }

    }

    @Override
    public void findBridge(BridgeUser user) {

    }
}

interface BridgeInterface<T> {
    void findBridge(T t);
}

class BridgeUser {

}
