package org.example.mybatis.spring;

import org.example.mapper.OrderMapper;
import org.example.mapper.UserMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.stereotype.Component;

/**
 * ClassName:MyBeanDefinitionRegistryPostProcessor
 * Package:org.example.mybatis.spring
 * Description: 使用BeanFactotyPostProcessor的子接口BeanDefinitionRegistryPostProcessor类注册beandefinition
 *
 *
 * @Date:2022/2/8 8:30
 * @Author:qs@1.com
 */
@Deprecated
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // 扫描mapper
        // 遍历mapper


        // UserMapper
//        AbstractBeanDefinition userMapperBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
//        userMapperBeanDefinition.setBeanClass(MyFactoryBean.class);
//        userMapperBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue(UserMapper.class);
//        registry.registerBeanDefinition("userMapper", userMapperBeanDefinition);
//
//        // OrderMapper
//        AbstractBeanDefinition orderMapperBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
//        orderMapperBeanDefinition.setBeanClass(MyFactoryBean.class);
//        orderMapperBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue(OrderMapper.class);
//        registry.registerBeanDefinition("orderMapper", orderMapperBeanDefinition);

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // do nothing
    }
}
