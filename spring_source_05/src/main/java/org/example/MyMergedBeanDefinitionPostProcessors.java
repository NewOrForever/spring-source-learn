package org.example;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * ClassName:MyMergedBeanDefinitionPostProcessors
 * Package:org.example
 * Description: 该接口操作的时合并后的beandefinition（beandefinition的后置处理，主要操作属性）
 *
 * @Date:2022/1/5 15:16
 * @Author:qs@1.com
 */
@Component
public class MyMergedBeanDefinitionPostProcessors implements MergedBeanDefinitionPostProcessor {
    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
        if ("userService".equals(beanName)) {
            // 属性注入，需要setter方法
            OrderService orderService = new OrderService();
            System.out.println(orderService);
            beanDefinition.getPropertyValues().add("orderService", orderService);
            // 设置初始化方法（如果bean实现了InitializingBean且实现了afterProperties方法，则这个初始化方法不会执行）
            beanDefinition.setInitMethodName("executeInitMethod");
            System.out.println("beandefinition后置处理。。。");
        }
    }

}
