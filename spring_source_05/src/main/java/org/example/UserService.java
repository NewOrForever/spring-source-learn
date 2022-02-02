package org.example;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * ClassName:UserService
 * Package:org.example
 * Description:
 *
 * @Date:2021/12/31 15:29
 * @Author:qs@1.com
 */
@Component
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//@DependsOn("orderService")
public class UserService {
    private OrderService orderService;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public UserService() {
        System.out.println(0);
    }

    public UserService(String xxx) {
        System.out.println(xxx);
    }

    public UserService(OrderService orderService) {
        this.orderService = orderService;
        System.out.println(1);
    }

    public void test() {
        String str =  this.orderService == null ? "userservice property orderservice is null" : this.orderService.toString();
        System.out.println("test - " + str);
    }

    public void executeInitMethod() {
        System.out.println("this is executeInitMethod");
    }
}
