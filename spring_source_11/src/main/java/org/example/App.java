package org.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 配置类源码解析
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        /**
         *
         * 是否配置类？lite、full
         *  - @Configuration
         *      - proxyBeanMethods属性为true（默认为true）会设置一个full属性，false则设置为lite
         *  - @Component
         *  - @ComponentScan
         *  - @Import
         *  - @ImportResource
         *  - 有@Bean方法
         *
         *
         * 每个配置类都会先封装成ConfigurationClass（其中会产生别的配置类去解析，也会将一些需要去解析的添加到ConfigurationClass的属性中）
         * 注意：在这里只有@ComponentScan才会去注册beandefintion，其他的都要在loadbeandefinition中去操作
         * 	- 该配置类有@Component注解且有内部类，内部类当作配置类再去解析（也会添加到paser的configurationClasses）
         * 	- 该配置类有@ComponentScan注解，扫描包注册beandefinition，检查这些beandefintion是不是配置类，是的话就再去进行解析
         * 	- 该配置类有@Import注解，执行processImports处理导入的类
         * 		- 实现了ImportSelector
         * 			- 有没有实现	DeferredImportSelector，是则添加到paser的deferredImportSelectorHandler属性中，推迟到每一批配置类解析完成后再去执行
         * 			- 否则直接实现selectImports()方法，返回的类再去执行processImports方法
         * 		- 实现ImportBeanDefinitionRegistrar，添加到当前配置类的importBeanDefinitionRegistrars属性中，在loadbeandefiniton中去执行
         * 		- 普通类则会当成配置类来进行解析
         * 	- 该配置类有@ImportResource注解，将xml文件resource添加到当前配置类的importedResources属性中，在loadbeandefinition中解析xml
         * 	- 该配置类有@Bean方法，将方法封装成BeanMethod放入当前配置类的beanMethods属性
         * 	- 该配置类实现的接口有@Bean方法，将方法封装成BeanMethod放入当前配置类的beanMethods属性
         * 	- 该配置类有父类，父类当成配置类进行解析
         * 	    - 如果该父类在前面的几步中没有通过扫描或这import导入进来那么就单纯作为父类而言它是不会有beandefinition的
         *
         *
         * loadBeanDefinitions：将解析时没有去生成beandeftnition的去生成
         *  - 导入的配置类（内部类或import）（配置类importedBy属性有值）
         *  - @Bean方法  (配置类beanMethods属性)
         *  - @ImprotResource导入的xml （配置类importResources属性）
         *  - 处理importBeanDefinitionRegistrars属性
         *
         * OrderService 导入AccountService，UserService也导入AccountService就会去mergeImportedBy
         * AccountService.importedBy=OrderService, AccountService.importedBy=UserService
         * importedBy这个属性是set，OrderService和UserService都添加到该属性
         *
         * 循环导入的话是会报错的：A导入B，B导入A
         *
         * beandefinition的覆盖
         *  @Component 两个类beanName相同，报错
         *  @Bean 只会生成一个
         *  @Bean和@Component都有 @Bean覆盖@Component的
         *
         * @Configuration
         *      - full：生成代理对象
         *      - ConfigurationClassPostProcessor增强Configuration生成代理类流程：
         *          1. postProcessBeanFactory -> 遍历所有beandefinition -> 找到所有是full的配置类 -> 生成cglib代理类
         *          2. createBeanInstance生成bean实例 -> 解析factoryMethod来创建bean -> 调用factoryMethod方法前会将方法设置到currentlyInvokedFactoryMethod
         *          -> 执行方法会进入代理类的回调类的拦截方法中（主要是BeanMethodInterceptor这个类）
         *          -> currentlyInvokedFactoryMethod判断，如果代理对象正在执行的方法就是正在创建Bean的factoryMethod，那就直接执行对应的方法得到对象作为Bean
         *          -> 如果代理对象正在执行的方法不是正在创建Bean的fatoryMethod，那就直接根据方法的名字去Spring容器中获取bean
         *
         *
         */
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AppConfig.class, AppConfiguration.class);
        context.refresh();

        System.out.println(context.getBean(MyImport.class.getName()));
//        System.out.println(context.getBean(MyImportSelector.class.getName()));                                // 抛错
//        System.out.println(context.getBean(MyImportBeanDefinitionRegistrar.class.getName()));       // 抛错
//        System.out.println(context.getBean(MyDeferredImportSelector.class.getName()));                  // 抛错
        System.out.println(context.getBean(MySelectImports.class.getName()));
        System.out.println(context.getBean(MyregisterBeanDefinitions.class.getName()));
        System.out.println(context.getBean(MyDeferredSelectImports.class.getName()));

        System.out.println(context.getBean("userService"));
        System.out.println(context.getBean("orderService"));
        System.out.println(context.getBean(AccountService.class.getName()));

        System.out.println(context.getBean("accountService"));



    }
}
