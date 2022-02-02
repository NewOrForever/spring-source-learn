package org.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        //context.registerShutdownHook();

        /**
         * bean的销毁
         *  销毁的是单例bean，原型bean交给jvm
         *
         *  bean的销毁方式：
         *      1. 手动： context.close();
         *      写在程序最后
         *      2. 注册：context.registerShurtdownHook();
         *          获取容器后就去注册，这种方式下是将销毁操作交给jvm，进程正常结束后销毁，但是强制结束进程不会触发（比如debug时直接终止）
         *      3. 实际两种方式执行的最终的代码时一致的，都是去执行AbstractApplicationContext.doClose()
         *      4. 销毁bean的时候先从需要销毁的缓存中剔除当前beanName，再强转成DisposableBean（实际是包装类DisposableBeanAdapter），最后会去
         *      调用销毁方法
         *      (DisposableBean) this.disposableBeans.remove(beanName);
         *      5. 销毁方法最终是由DisposableBeanAdapter调用执行
         *          bean.destroy(); ---> DisposableBeanAdapter.destroy();
         *
         *  什么样的bean是需要被销毁的bean
         *      1. 实现DisposableBean
         *      2. 实现AutoClosable
         *      3. 实现DestructionAwareBeanPostProcessor且requiresDestruction(bean)返回true
         *          - InitDestroyAnnotationBeanPostProcessor中使得拥有@PreDestroy注解了的方法就是DisposableBean，注解多个方法的话都会执行
         *      4. BeanDefinition中指定了destroyMethod
         *          - merge节点设置destroyMethodName="(inferred)", bean再写个close方法或者shutdown方法
         *              设置beandefinition的destroyMethodName这种方式如果bean的close方法和shutdown方法
         *              同时存在则只会执行close方法，源码中是先判断是否有close方法没有才会去找shutdown方法
         */
//        context.close();


        /**
         * 依赖注入
         *  自动注入
         *      1. autowire：bytype、byname（属性需要有set方法）
         *          - 什么样的属性能进行自动注入？
         *              1.该属性有对应的set方法
         * 		    2.没有在ignoredDependencyTypes中
         * 		    3.如果该属性对应的set方法是实现的某个接口中所定义的，那么接口没有在ignoredDependencyInterfaces中
         * 		    4.属性类型不是简单类型，比如int、Integer、int[]
         * 		    5.setxxx -> pd.getName() ---> xxx, pvs不包含xxx，这种情况也不叫不能属性注入，这是在merged拓展点手动注入的
         *          - bytype：根据set方法的参数类型来找bean，找到后传入set方法中
         *              1. 设置方法名称为setOrderService123(OrderService orderService)，能找到bean（根据参数类型OrderService来找bean）
         *              2. set方法有多个参数是不能自动注入的
         *                  能被PropertyDescriptor识别：set方法的定义是：方法参数个数为1个，并且 （方法名字以"set"开头并且方法返回类型为void）
         *          - byname：是根据set方法名（如：setOrderService，则会根据orderService，setOrderService123则会根据orderService123）作为beanName找bean
         *      2. @Autowired注解
         *          - 注解位置
         *              1. 属性上：先根据属性类型去找Bean，如果找到多个再根据属性名确定一个
         *              2. 构造方法上：先根据方法参数类型去找Bean，如果找到多个再根据参数名确定一个
         *              3. set方法上：先根据方法参数类型去找Bean，如果找到多个再根据参数名确定一个
         *          - 找注入点（如果bean的类型是java.开头（比如String这类）那么这个bean就不需要进行依赖注入）
         *              0. 找注入点其实是在AutowiredAnnotationBeanPostProcessor的merged方法就开始了，在postProcessProperties实际是从缓存
         *              中拿注入点然后再注入属性
         *              1. 遍历bean的所有方法和属性，如果方法或属性有@Autowired、@Value、@Inject
         *              2. static则不是注入点
         *              3. 桥接方法则不是注入点，需要找到原方法
         *                  创建一个UserServiceInterface<T>泛型接口建个void setOrderService(T t)方法，UserService实现它
         *                  编译后，view -》show bytecode查看字节码信息：
         *                  原方法：
         *                  public setOrderService(Lorg/example/OrderService;)V
         *                  @Lorg/springframework/beans/factory/annotation/Autowired;()
         *                  桥接方法：
         *                    public synthetic bridge setOrderService(Ljava/lang/Object;)V
         *                  @Lorg/springframework/beans/factory/annotation/Autowired;()
         *              4. require属性决定当没有找到bean时是否报错
         */
        //  autowire: byname、bytype源码
        UserService userService = (UserService) context.getBean("userService");
        System.out.println(context.getBean("orderName")); // String等simpleProperty描述的类型
        userService.test();
        // bytype: 根据set方法参数的类型来找bean，找到bean传入set方法中
        // 设置方法名称为setOrderService123，能找到bean
        // bytype: simpleProperty: String。。。不会注入
        // pvs在merge扩展点bd.getProperValues().add("orderService", new OrderService());
        // 那么注入时会跳过该属性，属性值还是new OrderService()

        // byname: setOrderService123: 根据orderService123这个beanName去找bean，找不到bean


        // @Autowired注入点解析  AutowiredAnnotationBeanPostProcessor
        // setxxx(User, Order) 去方法注入点源码那边看pd
        // @Autowire注解属性，set方法merged，测试注解和merged哪个先给属性赋值
        // 断点在postprocessproperties处发现autowireannotation处理后beanwapper的orderservice有值了，但是最后apply后属性值被merged的pv给覆盖了

    }
}
