package org.example.spring;


import org.springframework.lang.Nullable;

/**
 * ClassName:BeanPostProcessor
 * Package:org.example.spring
 * Description:
 *
 * @Date:2021/12/22 14:41
 * @Author:qs@1.com
 */
public interface BeanPostProcessor {
    @Nullable
    default Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    @Nullable
    default Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
