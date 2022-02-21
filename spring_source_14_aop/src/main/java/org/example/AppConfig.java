package org.example;

import org.example.advice.MyBeforeAdvice;
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

    // 启动中会报错AppConfig还在创建中，但是进入aop的初始化后中，找所有advisor -> 找到getBean(advisor) -> @Bean走factoryMethod
    // -> getBean(factoryBeanName也就是AppConfig) -> 但是还在创建中，所以会在getSingleton方法中的beforeSingleton中报错
    // 所以需要@Component注解来定义，或者@Bean static方法来定义
//    @Bean
//    public static MyAdvisor myAdvisor() {
//        return new MyAdvisor();
//    }

    @Bean
    public MyBeforeAdvice myBeforeAdvice() {
        return new MyBeforeAdvice();
    }


}
