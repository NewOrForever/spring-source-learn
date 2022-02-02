package org.example;

import org.example.destroy.Order;
import org.example.destroy.User;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ClassName:UserService
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/6 15:45
 * @Author:qs@1.com
 */

public class UserService {//implements UserServiceInterface<OrderService> {

//    @Autowired(required = false)
    @Autowired
    private OrderService orderService123;
    private User user;
    private Order order;

    // 方法参数有两个，PropertyDescriptor识别不了
    @Autowired
    public void setxxx(User user, Order order){
        this.user = user;
        this.order = order;
    }

    // PropertyDescriptor识别得到
    @Autowired
    public void setxxxxx(User user) {
        System.out.println("xxxxxxxxxxxxxxxxxx");
    }

//    @Autowired
//    public void xxx(){
//        System.out.println("没有参数");
//    }



//    private User user;
//    private Order order;
    //private String orderName;
    // autowire自动注入时需要set方法
//    public void setOrderService(OrderService orderService) {
//        this.orderService = orderService;
//    }

//    public void setOrderName(String orderName) {
//        this.orderName = orderName;
//    }

    // 能被PropertyDescriptor识别：set方法的定义是：方法参数个数为1个，并且 （方法名字以"set"开头并且方法返回类型为void）
//    public void setUser(User user, Order order) {
//        this.user = user;
//        this.order = order;
//    }

    public void test() {
        System.out.println(this.orderService123);
        System.out.println(this.user);
        System.out.println(this.order);
//        System.out.println(this.orderName);
    }

    public void setOrderService123(OrderService orderService) {
        this.orderService123 = orderService;
    }
}
