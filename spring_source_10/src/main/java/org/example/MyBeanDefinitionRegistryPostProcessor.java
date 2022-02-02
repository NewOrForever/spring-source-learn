package org.example;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.stereotype.Component;

/**
 * ClassName:MyBeanDefinitionRegistryPostProcessor
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/25 15:51
 * @Author:qs@1.com
 */
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("xxxxxxxxxxxxxxxxxxxxx");
//        AbstractBeanDefinition beanDefinition =
//                BeanDefinitionBuilder.genericBeanDefinition(MyBeanDefinitionRegistryPostProcessor.class).getBeanDefinition();
////        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(MyBeanDefinitionRegistryPostProcessor.class);
//        registry.registerBeanDefinition("bdrPostProcessor", beanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("===============");
        String[] allBeanNames = beanFactory.getBeanNamesForType(Object.class);
        for (String allBeanName : allBeanNames) {
            System.out.println("allBeanName=" + allBeanName);
        }
    }
}
