package org.example.qualifier;

import org.springframework.stereotype.Component;

/**
 * ClassName:RoundRobinStrategy
 * Package:org.example.qualifier
 * Description:
 *
 * @Date:2022/1/12 19:05
 * @Author:qs@1.com
 */
@Component
@RoundRobin
public class RoundRobinStrategy implements LoadBalance{

    @Override
    public String select() {
        return null;
    }
}
