package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * ClassName:UserService
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/17 15:13
 * @Author:qs@1.com
 */
@Component
//@Lazy
public class UserService {
    private OrderService orderService;

    public UserService() {
        System.out.println(0);
    }

    @Autowired(required = false)
    public UserService(OrderService orderService) {
        System.out.println(1);
    }

    @Autowired(required = false)
    public UserService(OrderService orderService, OrderService orderService1) {
        System.out.println(2);
    }

    @Autowired(required = false)
    public UserService(OrderService orderService, OrderInterface orderService1, OrderService orderService3) {
        System.out.println(3);
    }
    @Autowired(required = false)
    public UserService(OrderService orderService, OrderService orderService1, OrderInterface orderService3) {
        System.out.println(4);
    }

    public void test() {
        System.out.println(testLookUp());
        System.out.println(testLookUp());
        System.out.println(testLookUp());
        System.out.println(testReplace());
    }

    @Lookup("orderService")
    public OrderService testLookUp() {
        return null;
    }

    public Object testReplace() {
        return "userService.testReplace";
    }

}
