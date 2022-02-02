package org.example.qualifier;

import org.springframework.stereotype.Component;

/**
 * ClassName:RandomStrategy
 * Package:org.example.qualifier
 * Description:
 *
 * @Date:2022/1/12 19:04
 * @Author:qs@1.com
 */
@Component
@Random
public class RandomStrategy  implements LoadBalance{

    @Override
    public String select() {
        return null;
    }
}
