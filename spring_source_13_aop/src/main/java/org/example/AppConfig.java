package org.example;

import org.example.proxyFactory.User;
import org.example.proxyFactory.advice.MyAroundAdvice;
import org.example.proxyFactory.advice.MyBeforeAdvice;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.AopContext;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

/**
 * ClassName:AppConfig
 * Package:org.example
 * Description:
 *
 * @Date:2022/2/12 14:16
 * @Author:qs@1.com
 */
@ComponentScan("org.example")
@EnableAspectJAutoProxy
//@Import(AnnotationAwareAspectJAutoProxyCreator.class)
public class AppConfig {

    // ProxyFactoryBean的使用
//    @Bean
//    public ProxyFactoryBean user() {
//        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
//        // 设置目标对象
//        proxyFactoryBean.setTarget(new User());
//        proxyFactoryBean.addAdvice(new MyAroundAdvice());
//        // 设置代理逻辑的beanName
//        proxyFactoryBean.setInterceptorNames("myBeforeAdvice");
//
//        return proxyFactoryBean;
//    }

    // 定义一个代理逻辑
    @Bean
    public MyBeforeAdvice myBeforeAdvice() {
        return new MyBeforeAdvice();
    }

    // 只能根据beanName来指定需要代理的bean
//    @Bean
//    public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
//        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
//        // 指定需要被代理的beanName的规则（pointcut）
//        beanNameAutoProxyCreator.setBeanNames("user*");
//        // advice的beanName
//        beanNameAutoProxyCreator.setInterceptorNames("myBeforeAdvice");
//        return beanNameAutoProxyCreator;
//    }

    @Bean
    public User user() {
        return new User();
    }

    // 这个bean可以通过@Import来简化写法
    // 是一个SmartInstantiationAwareBeanPostProcessor，初始化后中找所有advisor，然后看bean符合哪些advisor的pointcut
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    // 注册一个advisor（pointcut + advice）
    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor() {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.addMethodName("execute");

        DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor();
        defaultPointcutAdvisor.setPointcut(pointcut);
        defaultPointcutAdvisor.setAdvice(new MyAroundAdvice());
        return defaultPointcutAdvisor;
    }


}
