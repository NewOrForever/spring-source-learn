package org.example;

import org.aspectj.lang.annotation.DeclareParents;
import org.example.proxyFactory.User;
import org.example.proxyFactory.UserInterface;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * spring aop基础知识
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        /**
         * Aop的一些概念
         *  - ProxyFactory（spring aop中的类啊，不要用错了）封装jdk及cglib动态代理
         *   1. spring复制了一个版本的cglib代码进来
         *      - cglib（基于父子类）使用
         *          - 需要设置superClass
         *          - methodProxy.invoke  /  method.invoke   /   methodProxy.invokeSuper(o, objects)
         *          - o是代理对象
         *          - 不同方法对应不同代理逻辑
         *              1. Callback设置多个 - NoOp.Instance啥都不做的拦截器
         *              2. setCallbackFilter，返回的数字就是上面Callback数组的下标（根据方法名来返回不同的下标）
         *   2. jdk动态代理（基于接口）
         *      - 被代理类需要实现一个接口
         *      - 返回的是接口的类型
         *  3. ProxyFactory使用（封装了jdk和cglib，如果target实现了接口且setInterface则会用jdk，否则用cglib）
         *      - setTarget, setInterfaces(返回的是jdk动态代理对象)
         *      - addAdvice（before、after、around、throwing）
         *        - method.invoke不需要了
         *        - MethodInteceptor.invoke(invocation) ---> before ---> invovation.proceed()
         *          around如果invocation.proceed()没有写，那么在around后面添加的MethodInteceptor就不会进去了
         *        - 看基础使用代码
         *      - 如何设置不同方法不同的代理逻辑
         *          addAdviser(PointcutAdvisor)
         *              - getAdvice()返回MethodInteceptor
         *              - getPointcut(）return StaticMethodMatcherPoingcut()
         *              - advisor实际就是advice + pointcut
         *   4. ProxyFactory在spring中使用
         *       - @Bean ProxyFactoryBean
         *          - 需要自己指定被代理对象
         *          - setInterceptorNames方法可以设置advice的beanName
         *       - 如何自动让UserService生成的代理对象的代理逻辑自动的绑定某个MethodInteceptor?
         *          1. @Bean BeanNameAutoProxyCreator：实际是个BeanPostProcessor，在实例化前操作符合设置的规则的
         *              setBeanNames("userSe*")：符合这样的beanname设置代理逻辑
         *              setInterceptorNames("myBeforeAdvice")
         *          2. @Bean DefaultAdvisorAutoProxyCreator （或者@Import）是个BeanPostProcessor 初始化后方法中找到所有Advisor的bean匹配当前被代理的bean（UserService）判断UserService中是否满足Pointcut指定的匹配规则
         *              @Bean DefaultPointAdvisor设置切面和代理逻辑
         *
         *  - AspectJ编译（setting -> java compile -> acj编译器）
         *  - spring把Aspectj的代码直接复制过来用
         *      - 开启AspectJ：@EnableAspectJAutoProxy实际是注册了一个AnnotationAwareAspectJAutoProxyCreator的bean，所以也可以直接@Import这个类就行了
         *      - 切面类MyAspectJ得是一个bean不然没用的哦
         *      - before、after、afterreturnning、around、afterthrowing这几个advice的执行顺序需要注意一下
         *      - 具体看写的代码示例吧
         *
         *  - @Lazy注解代理对象使用
         *  targetSource：被代理对象的来源，可以存不同的被代理对象
         *  getTarget：获取被代理对像
         *
         *  - @DeclareParents使用（introduction advice）
         *      给UserService增加一个接口
         * @DecalreParents(value="UserService.getname()", defaultImpl = 实现类.class)
         * private UserServiceInterface userServiceInterface;
         *
         *
         *
         */
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserInterface user = (UserInterface) context.getBean("user");
        user.execute();

    }
}
