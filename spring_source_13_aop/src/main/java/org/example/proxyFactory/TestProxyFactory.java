package org.example.proxyFactory;


import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.example.proxyFactory.advice.MyAfterReturnningAdvice;
import org.example.proxyFactory.advice.MyAroundAdvice;
import org.example.proxyFactory.advice.MyBeforeAdvice;
import org.example.proxyFactory.advice.MyThrowsAdvice;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * ClassName:TestProxyFactory
 * Package:org.example.proxyFactory
 * Description: 测试ProxyFactory
 *
 * @Date:2022/2/11 15:22
 * @Author:qs@1.com
 */

public class TestProxyFactory {
    public static void main(String[] args) {
        User target = new User();

        /********************基础使用**********************/
//        ProxyFactory proxyFactory = new ProxyFactory();
//        proxyFactory.setTarget(target);
////        proxyFactory.setInterfaces(target.getClass().getInterfaces());

        // 执行这个责任链
        // 先添加的先执行，但是afterreturnning肯定是在方法执行完成后再执行的，throw也肯定是在有异常抛出时执行
        // 所以就剩around和before了，哪个在前就先执行哪个，方法执行完成后先执行afterreturnning在执行around after
        // around实现MethodInterceptor，invocation.proceed()方法必须写上不然链路就断掉了，target的方法也不会执行了
        // throws只能拦截参数里定义的异常类型
        // 方法有异常的话，afterreturnning和around after就执行不了了（在异常后面的代码肯定就是执行不了的啊）
//        proxyFactory.addAdvice(new MyAroundAdvice());
//        proxyFactory.addAdvice(new MyBeforeAdvice());
//        proxyFactory.addAdvice(new MyAfterReturnningAdvice());
//        proxyFactory.addAdvice(new MyThrowsAdvice());
//
//        User proxyUser = (User) proxyFactory.getProxy();
//        proxyUser.execute();

        /*********************如何设置不同方法不同的代理逻辑***********************/
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAdvisors(new PointcutAdvisor() {
            // 切点
            @Override
            public Pointcut getPointcut() {
                return new StaticMethodMatcherPointcut() {
                    @Override
                    public boolean matches(Method method, Class<?> targetClass) {
                        if ("execute".equals(method.getName())) {
                            return true;
                        }
                        return false;
                    }
                };
            }

            // 代理逻辑
            @Override
            public Advice getAdvice() {
                return new MyBeforeAdvice();
            }

            @Override
            public boolean isPerInstance() {
                return false;
            }
        });
        User proxyUser = (User) proxyFactory.getProxy();
        proxyUser.execute();




    }
}
