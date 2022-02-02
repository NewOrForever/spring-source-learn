package org.example;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

/**
 * ClassName:AppConfig
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/10 18:18
 * @Author:qs@1.com
 */
@ComponentScan("org.example")
@PropertySource("spring.properties")
public class AppConfig {
//    @Bean({"orderService11", "orderService12", "orderService"})
//    public OrderService orderService1() {
//        return new OrderService();
//    }

    // 数组第一个为beanName
    // orderService22 -> orderService
    // orderService21 -> orderService
    // 所以这个和@Component注解的OrderService是一个bean
//    @Bean({"orderService", "orderService22", "orderService21"})
//    public OrderService orderService2() {
//        return new OrderService();
//    }

//    @Bean({"orderService12", "orderService22", "orderService"})
//    public OrderService orderService2() {
//        return new OrderService();
//    }
//    @Bean
//    @Primary
//    public OrderService orderService1() {
//        return new OrderService();
//    }

    @Bean(autowireCandidate = false)
    public UserService userServicexxx(){
        return new UserService();
    }

}
