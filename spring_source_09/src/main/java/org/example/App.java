package org.example;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.annotation.Order;

import java.util.function.Supplier;

import static org.springframework.beans.factory.support.AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR;

/**
 * 推断构造方法
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        /**
         * 测试时：UserService使用@Lazy注解，使其在使用时才去创建bean，容器初始化的时候不加载bean，这样便于测试
         *
         * 多个有参构造方法时会报错
         *
         * 指定构造方法：
         *  getBean指定参数
         *  @Autowired指定
         *
         *  1. 默认情况，用无参构造方法，或者只有一个构造方法就用那一个
         *  2. 程序员指定了构造方法入参值，通过getBean()或者BeanDefinition.getConstructorArgumentValues()指定，那就用所匹配的构造方法
         *  3. 程序员想让spring自动选择构造方法以及构造方法的入参值，autowire="constructor"
         *  4. 程序员通过@Autowired注解指定了某个构造方法，但是希望spring自动找该构造方法的入参值
         *
         *  手工去registerBeanDefinition
         *      BeanDefinition.getConstructorArgumentValues().addGenericArgumentValue(new OrderService)
         *      bd.setAutowireMode()
         *
         *  xml
         *  autowire="constructor"
         *
         *  supplier
         *          beanDefinition.setInstanceSupplier(new Supplier<Object>() {
         *             @Override
         *             public Object get() {
         *                 return new OrderService();
         *             }
         *         });
         *
         *  resolvedConstructorOrFactoryMethod：缓存构造方法
         *  constructorArgumentsResolved：缓存该构造方法的参数值，找到的是无参构造方法时，该值是false
         *
         *
         * @ConstructorProperties
         *
         * @Bean：factorymethodname
         * 		 1. 如果方法不是static的，那么解析出来的BeanDefinition中：
         * 		  	factoryBeanName为AppConfig所对应的beanName，比如"appConfig"
         * 		 	factoryMethodName为对应的方法名，比如"aService"
         * 		 	factoryClass为AppConfig.class
         * 		 2. 如果方法是static的，那么解析出来的BeanDefinition中：
         * 		 	factoryBeanName为null
         * 		 	factoryMethodName为对应的方法名，比如"aService"
         * 		 	factoryClass也为AppConfig.class
         *
         * @LookUp注解：这样在UserService单例，OrderService原型时，test方法能生成不同的OrderService对象
         *   @Lookup("orderService")
         *     public OrderService testLookUp() {
         *         return null;
         *     }
         *
         * xml方式方法替换
         *  <bean id="myMethodReplacer" class="org.example.MyMethodReplacer"/>
         *
         *     <bean id="userService" class="org.example.UserService">
         *         <replaced-method name="testReplace" replacer="myMethodReplacer"></replaced-method>
         *     </bean>
         *
         *
         */

        // getBean指定参数
//        context.getBean("userService", new OrderService());
//        context.getBean("userService", new OrderService(), new OrderService());

        // @Autowired注解指定
        // 当指定多个@Autowird时，required属性必须是false，否则会报错
        // 只要有一个required=true,就不能在有@Autowired指定第二个了
//        context.getBean("userService");

      /* // 手工去registerBeanDefinition
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        beanDefinition.setBeanClass(UserService.class);
//        beanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
//        beanDefinition.setInstanceSupplier(new Supplier<Object>() {
//            @Override
//            public Object get() {
//                return new OrderService();
//            }
//        });
//        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(new OrderService());
//        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(new OrderService());
//        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(new OrderService());
        beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(1, new OrderService());
//        beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(2, new RuntimeBeanReference("orderService"));
//        beanDefinition.setAutowireMode(AUTOWIRE_CONSTRUCTOR);
        context.registerBeanDefinition("userService", beanDefinition);*/

//        UserService userService = (UserService)context.getBean("userService", new OrderService());
        UserService userService = (UserService)context.getBean("userService");
//        UserService userService2 = (UserService)context.getBean("userService");
        System.out.println(userService);
        userService.test();

        /**
         *
         * getBean指定参数：
         *      - if (resolvedValues != null)这个判断不会进去，配置autowiremode也没用，只会去精确的匹配参数个数相同的构造方法
         *      - 不会进行缓存
         * beandefinition指定参数：
         *      - if (resolvedValues != null)这个判断会进去，先从beandefinition中找参数值，没找到再根据autowiring来判断是否去beanfactory中找bean，false的话
         *      抛错continue遍历下一个candidate构造方法
         *  没有@Autowired注解：
         *      - 只有一个无参构造方法或多个构造方法，返回null，需要配置autowiremode=constructor才能让autowiring=true
         *      - 只有一个有参构造方法，返回该构造方法，不需要配置autowiremode，autowiring也是true
         *  有@Autowired注解：
         *      - 返回一个required=true的构造方法或者多个required=false（这种情况无参构造方法不管有没有注解也会一起返回）的构造方法，不管
         *      autowiremode有没有配置autowiring都为true可以自动找bean
         *      - 先从beandefinition中找参数值，没找到再去beanfactory中找bean
         *  什么都没有指定的情况下
         *      - 如果有无参构造方法则autowireConstructor都不会进去直接调用instantiateBean来使用无参构造方法
         *
         *  整个的优先级：如果getbean指定参数则走精确匹配参数个数的逻辑、如果 @Autowired指定则遍历指定的构造方法先根据
         *  beandefinition找参数值，没找到则根据autowiring来判断是否需要去beanfactory中找，false则抛错continue遍历下一个构造方法
         *
         *
         * 没有@Autowired注解, 无参和有参构造方法都有
         *  1. 配置了autowiremode：会往下走，遍历所有构造方法，参数多的满足条件后break，最终用的构造方法是参数多的那个
         *  2. 没有配置autowiremode：有无参构造方法时就用无参构造方法，没有的话就会报错
         *  3. beandefinition指定参数：
         *      - 配置autowiremode：指定1个参数，如果当前构造方法有两个，那么第一个从bd中根据参数类型找，第二个依赖注入自动找符合的bean
         *      - 没有配置autowiremode：找到的是只有1个参数且参数类型匹配的构造方法
         *  4. beandefinition和getbean都指定了参数：走getBean指定参数的情况
         *
         * 有@Autowired注解，无参和有参构造方法都有，无参没加注解
         *  1. 一个required=true
         *      - 配置不配置autowiremode都是一样的，因为这种情况autowiring始终是true，所以最后在valueholder=null时都会去beanfactory找bean（就是依赖注入）
         *      - beandefinition指定参数：其实也是一样的，先从beandefinition中找参数值找到存起来，没找到再去找bean
         *  2. 多个required=false
         *
         *
         */


    }
}
