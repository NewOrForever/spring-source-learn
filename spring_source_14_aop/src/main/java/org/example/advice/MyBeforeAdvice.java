package org.example.advice;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * ClassName:MyBeforeAdvice
 * Package:org.example.proxyFactory.advice
 * Description:
 *
 * @Date:2022/2/11 16:49
 * @Author:qs@1.com
 */
public class MyBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("执行before。。。");
        /**
         * method.invoke()在before、afterreturnning、throwing中不用写了，如果写了的话就会执行两遍这个方法了
         * around实现了MethodInterceptor，还是需要invocation.proceed()的，不然这个链路就执行不下去了
          */

    }
}
