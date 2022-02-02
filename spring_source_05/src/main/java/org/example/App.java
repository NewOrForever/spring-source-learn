package org.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.util.ClassUtils;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        /**
         * 当UserService是singleton的时候，UserService是在初始化容器的时候就已经创建好了单例bean
         * 所以context.GetBean方法就是从singletonObjects中获取了单例bean，不用再去创建bean了，也就是
         * 说不需要再去调用构造方法了，所以这里不论getbean的args传什么都是不会走到构造方法中去的
         * 只有当UserService是prototype的时候才会走
         */
//        UserService userService1 = (UserService) context.getBean("userService");
//        UserService userService2 = (UserService) context.getBean("userService", "xxx");
//        UserService userService3 = (UserService) context.getBean("userService", new OrderService());
//        userService3.test();

        /**
         *          别名存储数据格式
         *         @Bean({"userservice", "userservice1", "userservice2"})
         *                 map<"userservice1", "userservice">
         *                 map<"userservice2", "userservice">
         *          transformedBeanName
         *          最后得到的beanName还是userService
         *
         */
//        User user = (User) context.getBean("user1");
//        user.test();

        /**
         *   isdependent(beanname,dep)  beanname是不是呗dep依赖了
         *   dependentBeanMap：某个bean被哪些bean依赖了
         *   dependenciesForBeanMap：某个bean依赖了哪些bean
         *   当UserService依赖了OrderService的同时OrderService依赖了UserService时就会进入循环依赖
         * @DependOn("orderService")
         */


        /**
         * scope: singleton, prototype, 其他（requestscope、sessionscope）(mvc、springboot时用)
         * requestscope和sessionscope原理其实和singleton一样的：使用方法不同而已，getAttribute，setAttribute
         */
        // scope：requestscope、sessionscope
        // webapplicationcontextutils
        // registerscope

        /**
         * doResolveBeanClass：解析beanClass(String，初始化容器创建beandefinition时通过asm技术来解析class文件，此时还未加载class，所以没有class对象),
         * 而在getbean的时候需要去开始创建bean，需要去用classloader来将beanclass解析成class对象。
         * AbstractBeanFactory中beanClassLoader有个默认的值：
         *  1. 优先返回当前线程中的ClassLoader
         *  2. 线程中类加载器为null的情况下，返回ClassUtils类的类加载器
         *  3. 如果ClassUtils类的类加载器为空，那么则表示是Bootstrap类加载器加载的ClassUtils类，那么则返回系统类加载器
         *         context.getBeanFactory().setBeanClassLoader(customclassloader);
         *         Thread.currentThread().setContextClassLoader(线程设置的classloader);    // tomcat也是使用这个方法来这是类加载器的
         *         Thread.currentThread().getContextClassLoader();
         *         ClassLoader cl = ClassUtils.class.getClassLoader();
         *         if (cl == null) {
         *             // 类加载器时bootstrap，此时类加载器就是null，这里看后面的jvm课程再看看吧
         *         }
         */


        /**
         * createbean大致流程：
         *  resolveBeanClass：创建beanClass的class对象（evaluatebeandefinitionstring：解析spring表达式）
         *  resolveBeforeInstantiation：InstantiationAwareBeanPostProcessor接口的实现执行（实例化前）
         *  doCreateBean：
         *      createBeanInstance：通过推断构造方法创建出bean
         *      applyMergedBeanDefinitionPostProcessors：MergedBeanDefinitionPostProcessor接口的实现执行（beandefinition的后置处理）
         *      populateBean：属性注入(spring自带的依赖注入)（在属性注入前，会去执行实例化后的操作）、InstantiationAwareBeanPostProcessor.postProcessproperties() (解析@Autowired)
         *      initializeBean：初始化前（beanpostprocessor）、初始化（InitializingBean，或者bd.setInitMethodName）、初始化后
         */
//        UserService userService = (UserService) context.getBean("userService");
//        userService.test();

        /**
         * spring自带的依赖注入：autowire:bytype,byname
         * 需要有set方法
         * bytype：获取bean所有的set方法，根据set方法的参数的类型找bean，找到就注入set方法
         * byname：获取bean所有的set方法，比如setOrderService，取orderService去找bean，找到就注入set方法
          */
        User user = (User) context.getBean("user");
        user.test();

        /**
         * @Autowired 起作用是因为spring在populateBean的时候有个拓展点 InstantiationAwareBeanPostProcessor.postProcessproperties()
         * AutowiredAnnotationBeanPostProcessor实现了这个接口执行postProcessproperties方法
         * 注意：
         * 使用xml配置的时候需要加 <context: annotation-config/>，不然@Autowired注解不会生效
         * 既然spring有自带的依赖注入（autowire：bytype、byname）,那么为什么还要去用@Autowired？
         * 因为自带的依赖注入的原理是去找bean下所有的set方法（不管是否需要依赖注入），而使用@Autowired注解后，spring就知道使用注解的属性
         * 是需要进行依赖注入的，而不需要去扫描全部set方法，所以这样更加灵活更加效率。
         *
         */


        // propertyvalues
        /**
         * MergedBeanDefinitionPostProcessor这个扩展点执行下面的代码（给属性赋值），那么在InstantiationAwareBeanPostProcessor.postProcessproperties()这个
         * 扩展点（有个AutowiredAnnotationBeanPostProcessor会去处理@Autowired，在这里会判断有这个注解的属性需不需要去跳过属性赋值），如果
         * 前面的扩展点执行了下面的代码则会跳过
         * beandefinition.getPropertyValues().add("propertyName", value)
         */

        // @postconstruct：初始化前（InitDestroyAnnotationBeanPostProcessor）
        /**
         * 初始化前执行InitDestroyAnnotationBeanPostProcessor的初始化前的方法：
         *  会去扫描bean的所有方法，验证是否有@PostConstruct注解（这个注解是在子类初始化的时候传进来的，看下面代码）
         * 	public CommonAnnotationBeanPostProcessor() {
         * 		setOrder(Ordered.LOWEST_PRECEDENCE - 3);
         * 		setInitAnnotationType(PostConstruct.class);
         * 		setDestroyAnnotationType(PreDestroy.class);
         * 		ignoreResourceType("javax.xml.ws.WebServiceContext");
         *        }
         *
         */

    }
}
