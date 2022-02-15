package org.example;

import org.example.advisor.MyAdvisor;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

/**
 * ClassName:AppConfig
 * Package:org.example
 * Description:
 *
 * @Date:2022/2/14 16:13
 * @Author:qs@1.com
 */
@ComponentScan("org.example")
@EnableAspectJAutoProxy
public class AppConfig {

    @Bean
    public MyAdvisor myAdvisor() {
        return new MyAdvisor();
    }



}
