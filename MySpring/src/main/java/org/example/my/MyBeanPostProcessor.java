package org.example.my;

import org.example.spring.BeanPostProcessor;
import org.example.spring.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ClassName:MyBeanPostProcessor
 * Package:org.example.my
 * Description:
 *
 * @Date:2021/12/22 14:49
 * @Author:qs@1.com
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

//    public Object postProcessBeforeInitialization(Object bean, String beanName) {
//        //System.out.println("before：beanname=" + beanName + " ------> postProcessBeforeInitialization。。。");
//        for (Field field : bean.getClass().getDeclaredFields()) {
//            if (field.isAnnotationPresent(MyValue.class)) {
//                MyValue myValueAnnotation = field.getAnnotation(MyValue.class);
//                String value = myValueAnnotation.value();
//                field.setAccessible(true);
//                try {
//                    field.set(bean, value);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return bean;
//    }

    public Object postProcessAfterInitialization(Object bean, String beanName) {

        if (beanName == "userService"){
            Class<?> clazz = bean.getClass();
            // 这里不高兴加cglib的包了，就用jdk动态代理吧，目标对象需要实现接口
            // classloader应该使用当前类来创建
            Object proxyInstance = Proxy.newProxyInstance(MyBeanPostProcessor.class.getClassLoader(), clazz.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("切面进入。。。");
                    Object result = method.invoke(bean, args);
                    System.out.println("切面结束。。。");
                    return result;
                }
            });
            return proxyInstance;
        }

        return bean;
    }
}
