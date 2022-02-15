package org.example;

import jdk.nashorn.internal.runtime.regexp.joni.ApplyCaseFoldArg;
import org.aopalliance.aop.Advice;
import org.example.advice.MyBeforeAdvice;
import org.example.advisor.MyAdvisor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.cglib.proxy.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Method;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {

        /**
         *  - ProxyFactory.getProxy()源码阅读
         * 	 jdk or cglib?
         * 	  if(targetClass.isInterface() || targetClass.isProxyClass())
         * 	  // proxyfactory.setTargetClass(UserInterface.class);  这样设置会报一些错的
         * 	  // targetClass是jdk动态代理生成的代理类
         * 	  - getProxy()
         *
         * 	  执行方法 -> 方法拦截器 -> Pointcut的classFilter匹配类型 -> MethodMatcher匹配方法（当isRuntime返回true时最终还要去匹配一次带参数的matches）
         * 		 factoryproxy.setExposeProxy(true)会将代理对象添加到ThreadLocal中
         * 		 test方法中AopContext.currentProxy() 拿到当前类的代理对象就不需要UserService去自己依赖自己了
         * 	  advice -> advisor -> advisor适配成methodinterceptor
         * 		 MethodMatcher 中isRuntime = true 最终要去matches(......args)这个方法
         *
         * 	  aop初始化后
         * 		 这里也要筛选一步advisor
         * 		 InstantiationModelAwarePointcutAdvisor解析before、after...注解
         */
        UserServiceInterface target = new UserService();
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        // proxyFactory.setTargetClass(UserServiceInterface.class);
        // proxyFactory.setProxyTargetClass(true);
        // proxyFactory.setExposeProxy(true);
//        proxyFactory.addAdvice(new MyBeforeAdvice());
        proxyFactory.addAdvisor(new MyAdvisor());
        UserService proxy = (UserService) proxyFactory.getProxy();
        proxy.execute();


        /**
         * 小的基础知识点：
         *  实例化子类的有参构造方法时，会不会先去调父类构造方法?
         *  还是会先去调用父类的无参构造方法的，父类没有无参构造方法的话会报错的(编译器就过不了)
         *  子类构造方法第一行默认有个super()只不过被省略了而已
         */

        /**
         * 源码阅读没理解的点：
         *  1. 判断使用jdk还是cglib动态代理
         *      config.isProxyTargetClass()
         *  2. 适配器模式
         */


    }
}
