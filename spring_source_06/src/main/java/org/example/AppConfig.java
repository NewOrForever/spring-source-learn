package org.example;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * ClassName:AppConfig
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/6 15:44
 * @Author:qs@1.com
 */
@ComponentScan("org.example")
public class AppConfig {

    @Bean
    public String orderName(){
        return "xxoo";
    }

    // autowire：bytype、byname注入时，bean需要设置set方法
//    @Bean(autowire = Autowire.BY_TYPE)
    @Bean
    public UserService userService() {
        return  new UserService();
    }

}
