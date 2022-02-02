package org.example;

import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * ClassName:MyMergedBeanDefinitionPostProcessor
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/10 13:59
 * @Author:qs@1.com
 */
@Component
public class MyMergedBeanDefinitionPostProcessor implements MergedBeanDefinitionPostProcessor {
    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
        if ("userService".equals(beanName)) {
            beanDefinition.getPropertyValues().add("orderService123", new OrderService());
        }
    }
}
