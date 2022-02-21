package org.example.objReference;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:ResourceRef
 * Package:org.example.objReference
 * Description:
 *
 * @Date:2022/2/18 12:47
 * @Author:qs@1.com
 */
public class ResourceRef {
    public static void main(String[] args) {
        List list = new ArrayList();
        Map<String, Object> map = new LinkedHashMap<>();
        list.add(map);
        Object oldValue = map.put("cacheKey", "new ref");

    }
}
