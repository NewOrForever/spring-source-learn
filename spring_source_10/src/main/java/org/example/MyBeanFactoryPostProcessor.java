package org.example;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * ClassName:MyBeanFactoryPostProcessor
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/25 15:46
 * @Author:qs@1.com
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//        beanFactory.getBeanDefinition("userService").setAutowireCandidate(false);
    }
}
