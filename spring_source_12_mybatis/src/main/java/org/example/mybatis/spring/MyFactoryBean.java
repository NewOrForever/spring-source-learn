package org.example.mybatis.spring;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

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

public class MyFactoryBean<T> implements FactoryBean<T> {

    private Class<T> mapperInterface;

    private SqlSession sqlSession;

    // 构造方法注入
    // beandefinition添加构造方法的参数值
    public MyFactoryBean(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    // 创建beandefiniton时设置了autowire的bytype注入
//    @Autowired
    public void setSqlSession(SqlSessionFactory sqlSessionFactory) {
        sqlSessionFactory.getConfiguration().addMapper(mapperInterface);
        this.sqlSession = sqlSessionFactory.openSession();;
    }

    @Override
    public T getObject() throws Exception {
//        return Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, new InvocationHandler() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                System.out.println("this is myfactorybean getobject");
//                return null;
//            }
//        });
        return sqlSession.getMapper(mapperInterface);
    }

    @Override
    public Class<?> getObjectType() {
        return mapperInterface;
    }


}
