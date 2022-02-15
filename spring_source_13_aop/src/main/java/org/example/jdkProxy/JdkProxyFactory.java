package org.example.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ClassName:JdkProxyFactory
 * Package:org.example
 * Description:jdk动态代理工厂
 * T：最好传入接口
 *
 * @Date:2022/2/11 13:33
 * @Author:qs@1.com
 */
public class JdkProxyFactory<T> {

    // 被代理的对象
    private T target;

    public void setTarget(T target) throws Exception {
        Class<?>[] interfaces = target.getClass().getInterfaces();
        if (interfaces == null || interfaces.length <= 0) {
            throw new Exception("not implement a interface");
        }
        this.target = target;
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (!"execute".equals(method.getName())) {
                    return method.invoke(target, args);
                }
                System.out.println("jdk动态代理start...");
                Object result = method.invoke(target, args);
                System.out.println("jdk动态代理end...");
                return result;
            }
        });
    }

}
