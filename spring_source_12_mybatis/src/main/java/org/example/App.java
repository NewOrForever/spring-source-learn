package org.example;

import org.example.mapper.UserMapper;
import org.example.mybatis.spring.MyFactoryBean;
import org.example.service.UserService;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

/**
 *  spring整合mybatis
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AppConfig.class);

        /**
         * FactoryBean创建bean知识点：
         *  容器初始化创建非懒加载单例bean的时候如果当前遍历到的bean是factoryBean则会用&xxx去getBean，拿到的是FactoryBean实例对象
         *  除非当前bean实现的是SmartFactoryBean,且实现的方法isEagerInit返回true，那么它就会在容器初始化的时候继续拿xxx去getBean，此时拿的是getObject
         *  否则就只能在下次getBean("xxx")的时候才能拿到getObject
         * FactoryBean依赖注入知识点（找UserMapper类型的beanName）：
         *
         */

        // 这段代码其实就是构造一个beanName为userMapper的FactoryBean，UseService的UserMapper属性填充的时候UserMapper和FactoryBean的getObjectType
        // 方法返回值相同就能执行getObject()方法返回值就是属性值
        // 通过beandefinition设置构造方法参数值（通过构造方法来给属性赋值）
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        beanDefinition.setBeanClass(MyFactoryBean.class);
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(UserMapper.class);
        context.registerBeanDefinition("userMapper", beanDefinition);

        context.refresh();

        UserService userService = (UserService) context.getBean("userService");
        userService.test();

    }
}
