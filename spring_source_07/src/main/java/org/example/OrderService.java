package org.example;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Priority;

/**
 * ClassName:OrderService
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/10 18:20
 * @Author:qs@1.com
 */
@Component
@Priority(6)
public class OrderService implements BeanNameAware, OrderServiceInterface {
    @Autowired
    private Order order;

    private String beanName;

    public void test() {
        System.out.println(order);
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public String toString() {
        return "OrderService{" +
                "beanName='" + beanName + '\'' +
                '}';
    }
}
