package org.example;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * ClassName:OrderService
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/17 15:13
 * @Author:qs@1.com
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OrderService implements OrderInterface{

    /**
     * 模拟使用spring.xml的factory-method来创建bean
     * 这就相当于和AppConfig的@Bean
     */
//    public static UserService create() {
//        return new UserService();
//    }

//    public  UserService create() {
//        System.out.println("userservice create ...");
//        return new UserService();
//    }


}
