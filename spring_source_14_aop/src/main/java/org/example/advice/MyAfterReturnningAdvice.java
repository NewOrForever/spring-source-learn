package org.example.advice;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * ClassName:MyAfterReturnningAdvice
 * Package:org.example.proxyFactory.advice
 * Description:
 *
 * @Date:2022/2/11 16:52
 * @Author:qs@1.com
 */
public class MyAfterReturnningAdvice implements AfterReturningAdvice {

    /**
     * 方法执行完成之后执行
     *
     * @param returnValue 方法return返回的值
     * @param method
     * @param args
     * @param target
     * @throws Throwable
     */
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("执行afterReturnning。。。");
    }
}
