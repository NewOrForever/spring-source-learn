package org.example.advice;

import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * ClassName:MyThrowsAdvice
 * Package:org.example.proxyFactory.advice
 * Description:
 *
 * @Date:2022/2/11 16:58
 * @Author:qs@1.com
 */
public class MyThrowsAdvice implements ThrowsAdvice {

    /**
     * throws这个advice里面的方法该怎么写接口里已经写好例子了，要么一个参，要么四个参
     * 这个接口没法定义方法，因为异常太多，只能让定好规范让程序员自己去定义
     * @param method
     * @param args
     * @param target
     * @param ex    需要拦截的异常，如果抛出的不是这个异常就拦截不了进不来哎
     */
    public void afterThrowing(Method method, Object[] args, Object target, NullPointerException ex) {
        // 先执行在抛出方法
        System.out.println("方法抛出异常后执行");
    }


}
