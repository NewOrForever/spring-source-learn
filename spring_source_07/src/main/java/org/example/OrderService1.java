package org.example;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.Priority;

/**
 * ClassName:OrderService1
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/12 14:17
 * @Author:qs@1.com
 */
@Component
@Priority(2)
public class OrderService1 implements BeanNameAware, OrderServiceInterface {

    private String beanName;

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public String toString() {
        return "OrderService1{" +
                "beanName='" + beanName + '\'' +
                '}';
    }
}
