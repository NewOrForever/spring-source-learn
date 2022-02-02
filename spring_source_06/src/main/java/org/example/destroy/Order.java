package org.example.destroy;

import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.stereotype.Component;

/**
 * ClassName:Order
 * Package:org.example.destroy
 * Description:
 *
 * @Date:2022/1/7 13:02
 * @Author:qs@1.com
 */
@Component
public class Order implements AutoCloseable {
    @Override
    public void close() throws Exception {
        System.out.println("order - AutoCloseable close 。。。");
    }
}
