package org.example;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * ClassName:AppConfig
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/26 13:52
 * @Author:qs@1.com
 */
@ComponentScan("org.example")
@PropertySource("classpath:spring.properties")
@Import({MyImport.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class, MyDeferredImportSelector.class})
public class AppConfig extends SuperConfig {

    /**
     * 内部类循环导入例子
     *  AppConfig有@Component注解：内部类生成ConfigurationClass时ImportBy=AppConfig
     *  内部类Import导入AppConfig：解析@Import注解时ImportBy=内部类
     */
//    @Import(AppConfig.class)
//    class AService{
//    }

//    @Bean
//    public UserService userService() {
//        return new UserService();
//    }
    @Bean
    public UserOverride userOverride() {
        System.out.println("0");
        return new UserOverride();
    }

    @Bean
    public UserOverride userOverride(OrderOverride orderOverride) {
        System.out.println("1");
        return new UserOverride();
    }
}
