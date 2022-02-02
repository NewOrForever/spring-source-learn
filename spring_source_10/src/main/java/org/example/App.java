package org.example;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * spring启动过程源码学习
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        /**
         *  ResourceEditorRegister
         *  Aware后置处理器
         * @Autowired执行两次
         * bytype执行一次，里面有个判断会过滤掉一些实现aware的
         *
         * debug一下registerResolvableDependency中this
         *
         *  lifecycle
         *      Lifecycle表示的是ApplicationContext的生命周期，可以定义一个SmartLifecycle来监听ApplicationContext的启动和关闭：
         *      start：context refresh时，finishRefresh方法中调用
         *      stop：关闭context（context.close()，或者注册关闭钩子context.registerShutdownHook(））时在doclose方法中调用
         *      getPhase()：该方法用于编组，返回相同值的是同一组，如果没有该方法，会把所有的LifeCycle分到一组去
         *      最后是遍历分组来foreach执行dostart方法
         *
         * messagesource：自己设置的beanName要messgeSource?
         *
         *  事件发布及事件监听
         *      ApplicationListenerDetector：fresh时处理自己定义的监听器，检查某个bean是不是applicationcontextListener
         *      SimpleApplicationEventMulticuster：事件发布器 -> 广播事件给监听器
         *          - multicastEvent执行时，如果设置了线程池则会利用线程池来并行的执行监听器
         *          - 该发布器是在fresh时initApplicationEventMulticaster中初始化的
         *      @EventListener: EventListenerMethodProcessor  -》 DefaultEventListenerFactory
         *          - 核心处理是EventListenerMethodProcessor的afterSingletonsInstantiated（实现SmartInitializingSingleton，所有非懒加载单例bean
         *          加载完后执行）
         *          - 会将@EventListener注解的方法适配成ApplicationListenerMethodAdapter
         *
         *
         *
         *  beanFactory.setDependencyComparator(AnnotationAwareOrderComparator.INSTANCE);
         *  new RootBeanDefinition(Class)
         *
         *  beanfactory.addBeanPostProcessor
         *
         *  invokeBeanFactoryPostProcessors：构建beanfactory
         *  BeanFactoryPostProcessor：beanfactory，不能注册beandefinition
         *  子接口：beandefinitoin，可以注册beandefiniton，先执行(先去注册bdMap嘛)
         *  ConfigurationClassPostProcessor实现了子接口
         *
         */
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);
        // 设置必须要有的环境变量，如果没有则会报错
        // 可以-Dggg=test或者弄个properties配置文件
        // context.getEnvironment().setRequiredProperties("ggg");

        /**
         *  invokeBeanFactoryPostProcessors中getBeanFactoryPostProcessors()获取的是这里添加的BeanFactoryPostProcessor
         *  不要去添加Bean,不然会重复执行方法
         */
        /*context.addBeanFactoryPostProcessor(new BeanDefinitionRegistryPostProcessor() {
            @Override
            public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
                System.out.println("000000000");
            }

            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
                System.out.println("111111111");
            }
        });*/

        context.refresh();

        // MessageSource的beanName问题验证：messageSource为beanName时，自己设置的MessageSource会赋值给AbstractApplicationContext的messageSource属性
        // 否则会使用默认的
        // ResourceBundleMessageSource ms = (ResourceBundleMessageSource) context.getBean("messageSource");



        // 发布一个事件，默认事件PayloadApplicationEvent
        context.publishEvent("111112123423");


//        context.close();



    }
}
