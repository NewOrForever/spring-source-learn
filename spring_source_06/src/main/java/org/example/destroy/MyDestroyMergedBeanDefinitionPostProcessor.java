package org.example.destroy;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * ClassName:MyDestroyMergedBeanDefinitionPostProcessor
 * Package:org.example.destroy
 * Description:
 *
 * @Date:2022/1/7 16:46
 * @Author:qs@1.com
 */
@Component
public class MyDestroyMergedBeanDefinitionPostProcessor implements MergedBeanDefinitionPostProcessor {
    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
        beanDefinition.setDestroyMethodName(AbstractBeanDefinition.INFER_METHOD);
    }
}
