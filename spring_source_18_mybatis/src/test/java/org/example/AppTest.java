package org.example;

import static org.junit.Assert.assertTrue;

import org.apache.ibatis.io.Resources;
import org.example.entity.User;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
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


}
