package org.example.framework;

import java.util.List;
import java.util.Random;

/**
 * 负载均衡
 */
public class LoadBalance<T> {

    public static <T> T random(List<T> list){
        Random random = new Random();
        int i = random.nextInt(list.size());
        return list.get(i);
    }
}
