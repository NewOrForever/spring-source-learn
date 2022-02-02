package org.example;

import org.aspectj.weaver.ast.Or;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName:AppConfiguration
 * Package:org.example
 * Description: 测试@Configuration生成代理对象
 *
 * @Date:2022/1/31 13:05
 * @Author:qs@1.com
 */
@Configuration(proxyBeanMethods = true)
public class AppConfiguration {
    @Bean
    public UserConfiguration userConfiguration() {

        System.out.println(orderConfiguration());
        System.out.println(orderConfiguration());
        System.out.println(orderConfiguration());

        return new UserConfiguration();
    }

    @Bean
    public OrderConfiguration orderConfiguration() {
         return new OrderConfiguration();
    }
}
