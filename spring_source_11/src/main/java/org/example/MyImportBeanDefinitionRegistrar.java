package org.example;

import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * ClassName:MyImportBeanDefinitionRegistrar
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/27 16:21
 * @Author:qs@1.com
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        AbstractBeanDefinition beanDefinition =
                BeanDefinitionBuilder.genericBeanDefinition(MyregisterBeanDefinitions.class).getBeanDefinition();
        String beanName = importBeanNameGenerator.generateBeanName(beanDefinition, registry);

        registry.registerBeanDefinition(beanName, beanDefinition);
    }
}
