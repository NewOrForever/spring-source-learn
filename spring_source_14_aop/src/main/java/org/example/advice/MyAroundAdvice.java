package org.example.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * ClassName:MyAroundAdvice
 * Package:org.example.proxyFactory.advice
 * Description:
 *
 * @Date:2022/2/11 17:06
 * @Author:qs@1.com
 */
public class MyAroundAdvice implements MethodInterceptor {


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("around执行前。。。");
        Object proceed = invocation.proceed();
        System.out.println("aroung执行后。。。");
        return proceed;
    }
}
