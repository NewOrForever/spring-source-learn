package org.example;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * ClassName:B
 * Package:org.example
 * Description:
 *
 * @Date:2021/12/24 20:57
 * @Author:qs@1.com
 */
//@Order(2)
public class B implements Ordered {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
