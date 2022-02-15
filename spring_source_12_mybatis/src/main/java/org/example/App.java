package org.example;

import org.example.mapper.UserMapper;
import org.example.mybatis.spring.MyFactoryBean;
import org.example.service.UserService;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
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
         *  先用xxx（单例池找及beanDefinition找）去类型匹配getObjectType，匹配到则是候选beanName
         *  没匹配到则再用&xxx去直接匹配类型，true则是候选beanName
         *
         */

        // 这段代码其实就是构造一个beanName为userMapper的FactoryBean，UseService的UserMapper属性填充的时候UserMapper和FactoryBean的getObjectType
        // 方法返回值相同就能执行getObject()方法返回值就是属性值
        // 通过beandefinition设置构造方法参数值（通过构造方法来给属性赋值）
//        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
//        beanDefinition.setBeanClass(MyFactoryBean.class);
//        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(UserMapper.class);
//        context.registerBeanDefinition("userMapper", beanDefinition);

        context.refresh();

        UserService userService = (UserService) context.getBean("userService");
        // 同一个事务下能公用sqlSession，不在同一个事务下sqlSession是不同的
        userService.test();
//        userService.testTran();

        /**
         * DefaultSqlSession线程不安全
         * sqlSessionTemplate线程安全
         *  - 每个线程都有一个自己的DefaultSqlSession
         *  - ThreadLocal：有没有DefaultSqlSession对象，没有则创建
         *  - 导致mybatis的一级缓存失效，需要开启事务才能生效
         *      TransactionSynchronizationManager.isSynchronizationActive()，不开启事务就不会去缓存sqlsession，每次都要去重新创建
         *
         *  不开启spring事务但是想要一级缓存有效？
         *  - @Bean  sqlSession sqlSessionFactory().openSession();
         *  - UserService中@Autowired SqlSession
         *  - sqlSession.selectOne("org.example.mapper.UserMapper.selectById")
         *
         *  mybatis一级缓存不建议使用？
         *  - 什么是一级缓存？
         *      - 执行两次userMapper.selectById，sqlsession是同一个，第二次执行时直接拿第一次的结果
         *  - 数据库隔离级别：读未提交
         *      - 执行两次userMapper.selectById，第一次执行返回2条数据，第二次执行的时候数据库中有3条数据，如果一级缓存有效的话
         *      第二次返回还是2条数据，也就是说一级缓存的优先级>数据库隔离级别
         *  - 综上，mybatis不建议使用一级缓存，可以去关掉
         *
         *  代替@MapperScan注解
         *      @Bean
         *     public static MapperScannerConfigurer mapperScannerConfigurer() {
         *         MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
         *         mapperScannerConfigurer.setBasePackage("org.example.mapper");
         *         return mapperScannerConfigurer;
         *     }
         */

        /**
         * 了解mybatis-spring源码怎么写的？
         */




    }
}
