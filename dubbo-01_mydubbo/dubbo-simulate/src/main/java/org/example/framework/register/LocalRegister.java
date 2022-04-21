package org.example.framework.register;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:LocalRegister
 * Package:org.example.framework.register
 * Description:
 *
 * @Date:2022/4/21 16:28
 * @Author:qs@1.com
 */
public class LocalRegister {
    private static Map<String, Class> localRegisterMap = new HashMap<>();

    public static void regist(String interfaceName, Class implClass) {
        localRegisterMap.put(interfaceName, implClass);
    }

    public static Class get(String interfaceName) {
        return localRegisterMap.get(interfaceName);
    }
}
