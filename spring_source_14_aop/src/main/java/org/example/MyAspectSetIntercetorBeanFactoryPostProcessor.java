package org.example;

import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import static org.springframework.aop.config.AopConfigUtils.AUTO_PROXY_CREATOR_BEAN_NAME;

@Component
public class MyAspectSetIntercetorBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (beanFactory.containsBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME)) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME);
            if (AnnotationAwareAspectJAutoProxyCreator.class.getName() == beanDefinition.getBeanClassName()) {
                // 开启了aop
                // 设置interceptorNames
                beanDefinition.getPropertyValues().addPropertyValue("interceptorNames", "myBeforeAdvice");
            }
        }
    }
}
