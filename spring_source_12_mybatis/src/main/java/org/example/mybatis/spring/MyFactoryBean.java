package org.example.mybatis.spring;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ClassName:MyFactoryBean
 * Package:org.example.mybatis.spring
 * Description:
 *
 * @Date:2022/2/7 16:29
 * @Author:qs@1.com
 */
public class MyFactoryBean implements FactoryBean {

    private Class mapperInterface;

    @Override
    public Object getObject() throws Exception {
        return Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        });
    }

    @Override
    public Class<?> getObjectType() {
        return mapperInterface;
    }


    public Class getMapperInterface() {
        return mapperInterface;
    }
    public void setMapperInterface(Class mapperInterface) {
        this.mapperInterface = mapperInterface;
    }
}
