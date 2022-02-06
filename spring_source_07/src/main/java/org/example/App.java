package org.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 依赖注入
 *
 */
public class App
{
    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = (UserService) context.getBean("userService");
        userService.test();

        // @Lazy
        /**
         * @Lazy注解
         *  1. 注入点有@Lazy注解时，注入的值是代理对象,当用到的时候才会去掉getTarget方法来匹配bean
         *      - 当@Lazy注解在方法上，方法的每个参数都是注入的代理对象
         *        @Autowired
         *        @Lazy
         *        public void setUser(User user, Order order) {
         *            this.user = user;
         *            this.order = order;
         *        }
         *      - 当@Lazy注解在方法参数上，有注解的参数注入的是代理对象，没有注解的参数注入的是bean
         *        @Autowired
         *        public void setUser( @Lazy User user, Order order) {
         *            this.user = user;
         *            this.order = order;
         *        }
         */

        // @Value ：占位符从Environment中找的，并不只是properties中，设置jvm参数 -Dmy=xxx
        // 解析spring表达式#{beanName}
        /**
         * @Value注解：
         *  1. 占位符${myorder}，从Environment找并不只是properties中，也可以设置jvm参数
         *      - 在properties文件中设置myorder=xxx，AppConfig那边需要配置@PropertySource("spring.properties")
         *      - 配置jvm参数：-Dmyorder=jvmxxx
         *      - 优先从jvm中获取
         *  2. spring表达式#{beanName}
         *      - 通过beanName从容器中找bean注入到属性中
         *  3. 源码中通过findValue找value，最后通过类型转换器来转换
         */

        // List、Map
        // List<T>、List<Object>
        /**
         * 依赖的类型（属性或方法参数的类型）是Array、Collection、Map
         *      - spring会将根据类型匹配到的matchingBeans通过类型转换器来转换到需要的类型
         *      - List<T> 、List<?>会报错
         *      - List<Object>会获取到所有的bean
         */

        /**
         * 根据类型找bean，找到的matchingBeans有多个时
         *  1. 先去通过@Primary筛选
         *      - 当有多个bean都有该注解时报错
         *  2. 再去@Priority筛选
         *      - 当两个bean优先级一样时报错
         *  3. 最后通过字段名或方法参数名去筛选
         */
        // primary
        // priority
        // 根据类型找到多个bean，先primary再priority最后再byname

        // matchingbeans的value可能时bean对象也肯能是beanclass

        // UserService注入userservice, setbeanname
        // 没有其他bean时才用自己
        /**
         * 自己注入自己（UserService依赖一个UserService类型的属性）
         * UserService类：private UserService userService;
         *  - 照理说肯定匹配的时beanName是userService的bean
         *  - 但是只有在没有找到其他bean时才会使用自己这个bean
         *  - 我这里还在AppConfig里设置了一个bean，获取的结果是uerServicexxx这个bean
         *      @Bean
         *     public UserService userServicexxx(){
         *         return new UserService();
         *     }
         *  - 源码里找匹配的bean时，最后才会去找自己注入自己，且当没有找到其他bean（result.isEmpty()）的时候才回去匹配
         */

        //autowiredcondidate 责任链模式
        /**
         * simple 检查autowiredcandidate属性 ---> generic检查泛型是否撇配 ---> qualifier检查@Qualifier的值是否匹配
         */


        // cached
        // Fidle和Method注入的时候cached是不同的，不同的两个对象AutowirdFieldElement、AutowireMethodElement分别处理的
        // 缓存什么时候进入：UserService原型，为啥要缓存beanname：OrderService原型
        // User依赖OrderService，UserService依赖OrderService，会进如cached吗? 不会的，Map<String, InjectionMetadata> injectionMetadataCache
        // 缓存的注入点是map，key是beanName，执行不同的bean的时候beanName不同取得的注入点（new AutowirdFieldElement）就不同
        // 只有key相同时才能取到相同的注入点，才能判断到该注入点的cached


        /**
         * bean同类型多个:
         *
         *      // beanName是orderService11
         *      // orderService12 -> orderService11
         *      // orderService -> orderService11
         *      @Bean({"orderService11", "orderService12", "orderService"})
         *     public OrderService orderService1() {
         *         return new OrderService();
         *     }
         *
         *     // 数组第一个为beanName
         *     // orderService22 -> orderService
         *     // orderService21 -> orderService
         *     // 所以这个和@Component注解的OrderService是一个bean
         *     @Bean({"orderService", "orderService22", "orderService21"})
         *     public OrderService orderService2() {
         *         return new OrderService();
         *     }
         */

        /**
         * @Resource注入点
         *  1. merged节点找到bean的所有resource注入点，并缓存
         *      - 当@Resource注解name属性不设置，则默认取field的name，Method的setXxx ---> xxx
         *      - 当@Resource注解type属性不设置，则默认是Object，源码中当是Object的时候会转成Field或Method第一个参数的类型
         *  2. postProcessProperties给bean注入属性时
         *      - 假设@Resource中没有指定name，并且field的name或setXxx()的xxx不存在对应的bean，那么则根据field类型或方法参数类型
         *      从BeanFactory去找（就是和@Autowired注入一样，根据类型去找bean）
         *      - 只有上述条件满足才会去根据类型找，优先还是根据name找（getBean(beanName, requiredType)）
         */


    }
}
