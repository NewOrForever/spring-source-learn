package java.learn;

import java.util.HashMap;
import java.util.Map;

/**
 * final修饰局部变量
 * 不能修改
 */
public class FinalVariableTest {
    public void test() {
        final Integer a = 2;
        final Map<String, Object> map = new HashMap<>();
        map.put("key", "value");
    }
}

