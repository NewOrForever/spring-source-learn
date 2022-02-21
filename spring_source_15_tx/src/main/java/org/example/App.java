package org.example;

import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.NamedThreadLocal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * ClassName:App
 * Package:org.example
 * Description:事务源码学习
 *
 * @Date:2022/2/16 21:06
 * @Author:qs@1.com
 */
public class App 
{
    public static void main( String[] args )
    {

//        ThreadLocal<Set<String>> strThreadLocal = new NamedThreadLocal<>("string thread local");
//        Set<String> strings = strThreadLocal.get();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = (UserService) context.getBean("userService");
        userService.test();




        /**
         *
         * TODO
         *      spring_source_14_aop调试跑一遍：initBeanFactory，isEligibleAdvisorBean
         *      - importaware  <-  ImportAwareBeanpostProcessor    finished
         *      传播机制测试
         *      MethodCache（Method, hashcode）, MethodClassCache（Method, Class）
         *          - 涉及到equals，hashcode，Map中的比较
         *
         * @EnableTransactionManagement
         *  - Proxy
         *      - 引入两个类
         *          1. AutoProxyRegistrar
         *              - 注册一个InfrastructureAdvisorAutoProxyCreator这个BeanPostProcessor
         *              - 这个BeanPostProcessor在初始化后中找定义了@Role(BeanDefinition.ROLE_INFRASTRUCTURE)的advisor bean（实际就是下面的配置类中定义的bean）
         *          2. ProxyTransactionManagementConfiguration
         *              - 定义了3个@Role(BeanDefinition.ROLE_INFRASTRUCTURE)的bean，其中一个是advisor
         *              - Pointcut
         *              - interceptor
         *
         *
         *  - AspectJ
         *
         *  BeanDefinition.ROLE_INFRASTRUCTURE
         * Role
         */


        /**
         * spring事务（添加advisor）：说到底还是得在初始化后中进行aop生成代理对象
         *    @Import引入两个bean，一个BeanPostProcessor，一个引入3个bean
         *
         *    开启spring事务后aspecj切面就解析不了了？测试一下
         *    TransactionAttributesource解析@Transactional注解
         *    advisor内部维护了一个pointcut -> ClassFilter.matches -> iscandidateclass判断有没有transactional注解
         *    parseTransactionannotation -> 构造rulebasedtransactionattribute
         *    TransactionalInterceptor.invoke
         *
         *
         * - 传播机制propagation
         *    - Propagation.REQUIRES_NEW
         *    - NEVER：已经有一个事务了，还要开一个的话就会报错
         *    - NOT_SUPPORT：已经有一个事务了，会挂起conn，不新建数据库连接，用jdbc或mybatis自己的数据库连接
         *
         *    spring事务管理器创建数据库连接conn
         *    conn.autocommit = false; // 手动提交
         *    conn.隔离级别
         *    conn放入ThreadLocal<Map>  datasource,conn连接
         *    target.test()执行sql1，sql2
         *
         *        a()
         *            挂起 -> 挂起对象.conn连接 ->
         *            spring事务管理器，创建数据库连接conn1
         *            conn1.autocommit=false;
         *            conn1.隔离级别
         *            conn1放入ThreadLocal<Map> datasource，conn1连接
         *            sql
         *            // 是Propagation.REQUIRED这个传播机制：那么a()和test()方法就在同一个数据库连接，set部分失败全局回滚不管是true还是false，全局都会回滚，如果
         *            // try...catch了的话，true则全局回滚，false则不全局回滚也就是都会commit（如果sql报错了，那么错误的sql不会提交）
         *            // 是Propagation.REQUIRES_NEW这个传播机制：那么a()方法就会新建一个数据库连接，两个方法各自提交回滚
         *            throw new Exception()
         *            conn1提交、回滚
         *            恢复 -> 挂起对象.conn连接 -> ThreadLocal<Map>
         *            rollback() -> true -> ThreadLocal中添加一个全局回滚为true的标记
         *
         *    sql3
         *
         *    提交 -> commit() -> ThreadLocal true -> 回滚
         *
         *    set部分失败全局回滚
         *
         *    // 拿事务名字
         *    TransactionSynchronizationManager.getCurrentTransactionName()
         *    // 拿事务的隔离级别
         *    TransactionSynchronizationManager.getCurrentTransactionIsolationLevel()
         *    TransactionSynchronizationManager.getSynchronizations()
         *
         *    createTransactionIFNecessary开启一个事务 -> getTransaction
         *
         *    timeout=1秒：sql执行了1秒还没有执行完就超时
         *
         *
         *    show status like '%Treads_connected%' 查看有几个线程连接了数据库
         *
         *
         *
         *
         *
         */

    }
}
