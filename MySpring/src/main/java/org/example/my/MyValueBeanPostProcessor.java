package org.example.my;

import org.example.spring.BeanPostProcessor;
import org.example.spring.Component;

import java.lang.reflect.Field;
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
public class MyValueBeanPostProcessor implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        //System.out.println("before：beanname=" + beanName + " ------> postProcessBeforeInitialization。。。");
        for (Field field : bean.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(MyValue.class)) {
                MyValue myValueAnnotation = field.getAnnotation(MyValue.class);
                String value = myValueAnnotation.value();
                field.setAccessible(true);
                try {
                    field.set(bean, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }


}
