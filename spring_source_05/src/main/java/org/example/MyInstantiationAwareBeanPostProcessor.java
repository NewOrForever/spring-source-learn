package org.example;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * ClassName:MyInstantiationAwareBeanPostProcessor
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/5 14:54
 * @Author:qs@1.com
 */
@Component
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    /**
     * spring提供的一个实例化前的一个扩展点
     * 如果返回的值为空，则继续去执行createbean的下面的流程
     * 如果返回的值不为空，则会跳过跳过一些步骤，直接执行初始化后这一步返回bean，但是这里返回的bean的类型要
     * 注意一下，避免类型强转的时候出错
     *
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if ("userService".equals(beanName)) {
            System.out.println("实例化前。。。");
            //return new User(); // 这样直接返回User对象的话，singletonObjects里存放的{"userService", User}
            return null;
        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if ("userService".equals(beanName)) {
            System.out.println("实例化后。。。");
        }
        return true;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        // 处理@Autowired、@Resource、@Value、@Inject
        return null;
    }

    /*********************BeanPostProcessor**********************/
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName + "初始化前。。。");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName + "初始化后。。。");
        return bean;
    }
}
