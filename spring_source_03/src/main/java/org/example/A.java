package org.example;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * ClassName:A
 * Package:org.example
 * Description:
 *
 * @Date:2021/12/24 20:56
 * @Author:qs@1.com
 */
//@Order(3)
public class A implements Ordered {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
