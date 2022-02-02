package org.example;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * ClassName:User
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/4 16:14
 * @Author:qs@1.com
 */
public class User {

    private OrderService orderService;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void test() {
        System.out.println(orderService);
    }

}
