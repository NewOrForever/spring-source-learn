package org.example;

import static org.junit.Assert.assertTrue;

import org.apache.ibatis.io.Resources;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
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
}
