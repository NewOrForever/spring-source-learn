package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

/**
 * ClassName:OrderService
 * Package:org.example
 * Description:
 *
 * @Date:2021/12/28 15:27
 * @Author:qs@1.com
 */
@Component
public abstract class OrderService {
//    @Autowired
//    private UserService userService;

    public OrderService() {
        System.out.println("orderService init ...");
    }

    public void test() {
        // UserService多例的情况下的不同
        /**
         * 调用三次test方法,userService的值是一样的，因为OrderService是单例的，它只会创建一次，因此
         * userService只会赋值一次
         */
        //System.out.println(userService);
        /**
         * 调用三次test方法，打印的值是不一样的，因为lookup会每次都去获取一次UserService，而UserService又是多例的，因此每次都会
         * 创建一个UserService
         */
        System.out.println(a());
    }

    /**
     * 有@LookUp注解，OrderService会生成的bean是cglib代理对象，执行方法的时候会
     * 进入拦截方法，拿到注解value值然后去getBean
     * @return
     */
    @Lookup("userService")
    public UserService a() {
        return null;
    }

}
