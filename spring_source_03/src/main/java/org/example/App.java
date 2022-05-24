package org.example;

import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.OrderComparator;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

import java.beans.PropertyEditor;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Description: 主要是讲spring的一些运用
 *
 * @Date:2021/12/23 14:05
 * @Author:qs@1.com
 */
public class App {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
//
//        beanDefinition.setBeanClass(UserService.class);
//        beanDefinition.setInitMethodName("test");
//        beanDefinition.setScope(DefaultListableBeanFactory.SCOPE_SINGLETON);
//        beanDefinition.setLazyInit(true);
//        context.registerBeanDefinition("userService", beanDefinition);
//
//        System.out.println(context.getBean("userService"));
//        System.out.println(context.getBean("userService"));

        /***********************BeanDefinitionReader****************************/
//        AnnotatedBeanDefinitionReader beanDefinitionReader = new AnnotatedBeanDefinitionReader(context);
//        beanDefinitionReader.registerBean(UserService.class);
//        System.out.println(context.getBean("userService"));
//
//        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(context);
//        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");
//        System.out.println(context.getBean("userService"));

        /***********************ClassPathBeanDefinitionScanner*************************/
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        context.refresh();
//        ClassPathBeanDefinitionScanner beanDefinitionScanner = new ClassPathBeanDefinitionScanner(context);
//        beanDefinitionScanner.scan("org.example");
//        System.out.println(context.getBean("userService"));

        /***************************DefaultListableBeanFactory****************************/
//        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
//        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
//        beanDefinition.setBeanClass(UserService.class);
//        defaultListableBeanFactory.registerBeanDefinition("userService", beanDefinition);
//        System.out.println(defaultListableBeanFactory.getBean("userService"));

        /****************国际化****************/
//        String message01 = context.getMessage("test", null, new Locale("en_CN"));
//        String message02 = context.getMessage("test", null, new Locale("CN"));
//        String message03 = context.getMessage("test", null, new Locale("en"));
//        System.out.println(message01);
//        System.out.println(message02);
//        System.out.println(message03);

//        ((UserService)(context.getBean("userService"))).test();

        /**************************资源加载**************************/
        //URL resource = App.class.getClassLoader().getResource("org/example");
//        Resource resource = context.getResource(" file:/E:/Workspaces/tuling/spring/spring_source_03/src/main/java/org/example/UserService.java");
//        Resource resource2 = context.getResource(" file://E:\\Workspaces\\tuling\\spring\\spring_source_03\\src\\main\\java\\org\\example\\AppConfig.java");
//        System.out.println(resource.contentLength());
//        System.out.println(resource2.contentLength());
//        System.out.println(resource.getFilename());
//        System.out.println(resource2.getFilename());
//
//        Resource resource1 = context.getResource("https://www.baidu.com");
//        System.out.println(resource1.contentLength());
//        System.out.println(resource1.getURL());
//
//        Resource resource3 = context.getResource("classpath:spring.xml");
//        System.out.println(resource3.contentLength());
//        System.out.println(resource3.getFilename());
//
//        Resource[] resources = context.getResources("classpath:org/example/*.class");
//        for (Resource resourceItem : resources) {
//            System.out.println(resourceItem.contentLength());
//            System.out.println(resourceItem.getFilename());
//        }

        /***********************获取运行时环境*************************/
//        ConfigurableEnvironment environment = context.getEnvironment();
//        Map<String, Object> systemEnvironment = environment.getSystemEnvironment();
//        System.out.println(systemEnvironment);
//
//        System.out.println("==============");
//
//        System.out.println(environment.getSystemProperties());
//
//        System.out.println("==============");
//
//        MutablePropertySources propertySources = environment.getPropertySources();
//        System.out.println(propertySources);
//
//        System.out.println("===============");
//
//        System.out.println(environment.getProperty("USERDOMAIN"));
//        System.out.println(environment.getProperty("sun.jnu.encoding"));
//        System.out.println(environment.getProperty("testname"));  // @PropertySource("classpath:spring.properties")
//
        /***********************事件监听器************************/
//        UserService userService = context.getBean("userService", UserService.class);
//        userService.test();
        /**********************类型转化************************/
        // jdk
//        StringToUserPropertyEditor stringToUserPropertyEditor = new StringToUserPropertyEditor();
//        stringToUserPropertyEditor.setAsText("库里");
//        User user = (User) stringToUserPropertyEditor.getValue();
//        System.out.println(user);

        // spring
//        DefaultConversionService defaultConversionService = new DefaultConversionService();
//        defaultConversionService.addConverter(new StringToUserConverter());
//        User user = defaultConversionService.convert("spring_convert", User.class);
//        System.out.println(user);

        // typeconverter
//        SimpleTypeConverter simpleTypeConverter = new SimpleTypeConverter();
//        simpleTypeConverter.registerCustomEditor(User.class, new StringToUserPropertyEditor());
//
//        DefaultConversionService conversionService = new DefaultConversionService();
//        conversionService.addConverter(new StringToPersonConverter());
//        simpleTypeConverter.setConversionService(conversionService);
//        System.out.println(simpleTypeConverter.convertIfNecessary("simpletypeconverter", User.class));
//        System.out.println(simpleTypeConverter.convertIfNecessary("100", Person.class));
//
//        UserService userService = (UserService) context.getBean("userService");
//        userService.typeConvert();

        /***************************比较器****************************/
//        A a = new A();
//        B b = new B();
        // 注解
//        AnnotationAwareOrderComparator annotationAwareOrderComparator = new AnnotationAwareOrderComparator();
//        System.out.println(annotationAwareOrderComparator.compare(a, b));

        // 实现Ordered接口
//        OrderComparator orderComparator = new OrderComparator();
//        System.out.println(orderComparator.compare(a, b));
//
//        List list = new ArrayList<>();
//        list.add(a);
//        list.add(b);
//        // 升序排序
//        list.sort(orderComparator);
//
//        System.out.println(list);


        //context.getBean("userService");

        /*************************元数据读取器*****************************/
        SimpleMetadataReaderFactory simpleMetadataReaderFactory = new SimpleMetadataReaderFactory();
        // 获取某个类的元数据读取器
        // 在new SimpleMetadataReader的时候会去创建SimpleAnnotationMetadataReadingVisitor来读取class文件的信息
        MetadataReader metadataReader = simpleMetadataReaderFactory.getMetadataReader(UserService.class.getName());
        // ClassMetaData
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        System.out.println(classMetadata.getClassName());
        System.out.println(classMetadata.getInterfaceNames()[0]);
        System.out.println(classMetadata.getMemberClassNames()[0]);
        System.out.println(classMetadata.getEnclosingClassName());

        // AnnotationMetaData
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        System.out.println(annotationMetadata.getAnnotationTypes());
        System.out.println(annotationMetadata.hasAnnotation(Indexed.class.getName()));
        System.out.println(annotationMetadata.hasMetaAnnotation(Indexed.class.getName()));
        System.out.println(annotationMetadata.getMetaAnnotationTypes(Component.class.getName()));

        /*****************************FactoryBean创建的bean不会经历bean完整的生命周期，而是经历初始化后的生命周期，为的还是aop吧**********************************/
        // spring是会将FactoryBean当成普通bean来处理的，singletonObjects中是有factoryBean的单例bean存在的
        // 调用getBean之后context的beanfactory的factoryBeanObjectCache中就会添加myFactoryBean -> UserService的缓存数据
        System.out.println(context.getBean("myFactoryBean"));       // 调用了FactoryBean的getObject
        System.out.println(context.getBean("&myFactoryBean"));    // 加了&后就是获取单例池中bean
        /**
         *  验证FactortyBean创建的对象的生命周期：
         *      创建一个MyBeanPostProcessor记录初始化前和初始化后
         *      发现UserService没有初始化前，从初始化后开始
          */



    }
}
