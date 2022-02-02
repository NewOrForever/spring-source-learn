package org.example;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * ClassName:AppConfig
 * Package:org.example
 * Description:
 *
 * @Date:2021/12/31 15:30
 * @Author:qs@1.com
 */
@ComponentScan("org.example")
@Configuration
public class AppConfig {

    @Bean(value = {"user", "user1", "user2"}, autowire= Autowire.BY_TYPE) // byname
    public User user(){
        return new User();
    }

}
