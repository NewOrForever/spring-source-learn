package org.example;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        context.refresh();
//        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(context);
//        reader.registerBean(UserService.class);
//        System.out.println(context.getBean("userService"));

        //AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.example");

//        OrderService orderService = (OrderService) context.getBean("orderService");
//        orderService.test();
//        orderService.test();
//        orderService.test();
        // scannedbd  beanclass
        // static inner class
        // lookup
        /****************************** 扫描2次 *****************************/
        /**
         * 该beanName已存在
         * 测试checkCandidate方法内部的isCompatible
         * 这里符合扫描了两次相同的class文件，所以是可以兼容的，返回false，表示不需要再去注册beandefinition了
         */
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("org.example");
        context.scan("org.example");
        context.refresh();

        /**
         * spring.components文件来存放类型全名和注解全名的key-value，在初始化AnnotationConfigApplicationContext的时候会加载到
         * componentsIndex，这样做的好处是比普通的扫描快，相当于是索引功能啦，当需要注册的bean较多的时候使用这个方式能提高扫描速度
         * 注意：使用索引扫描的时候，@Components注解还是需要的，一旦有了spring.components文件且有键值对时，spring就只会去解析这个文件，而不会再去
         * 以扫描文件的方式去找beandefinition,所以如果MyFactoryBean虽然注解了@Component，但是没有在spring.components中配置，此时MyFactoryBean
         * 就不会加载到
         */
//        UserService userService = (UserService) context.getBean("userService");
//        userService.test();

        // refresh  最后一个方法加载非懒加载单例bean，beandefinition.isabstract
//        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
//        System.out.println(classPathXmlApplicationContext.getBean("user"));
//        System.out.println(classPathXmlApplicationContext.getBean("user"));
//        System.out.println(classPathXmlApplicationContext.getBean("user"));

        // smartInitializationBean 生命周期：所有非懒加载单例bean创建后
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.example");

        // 实现smartfactorybean，方法iseagerinit设置返回true，则在容器启动的时候就会加载getobject方法创建bean
        /**
         *     源码：
        *       isEagerInit = (factory instanceof SmartFactoryBean &&
        * 		((SmartFactoryBean<?>) factory).isEagerInit());
        *
        * 		if (isEagerInit) {
        * 			getBean(beanName);
         * 	}
        */

        // 设置父容器：context.setparent，isFactoryBean
//        AnnotationConfigApplicationContext parentContext = new AnnotationConfigApplicationContext(ParentAppConfig.class);
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
////        context.register(UserService.class);
//        context.setParent(parentContext);
//        context.refresh();
//        context.getBean("userService");

        // getObjectForBeanInstance


    }
}
