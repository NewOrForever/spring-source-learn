package org.example;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;

/**
 * ClassName:MyReplaceOverrideMethodInterceptor
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/22 16:52
 * @Author:qs@1.com
 */
public class MyMethodReplacer implements MethodReplacer {
    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {

        return "MyMethodReplacer.reimplement";
    }
}
