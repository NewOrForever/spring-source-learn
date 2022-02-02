package org.example;

import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditor;
import java.util.*;

/**
 * ClassName:AppConfig
 * Package:org.example
 * Description:
 *
 * @Date:2021/12/23 13:39
 * @Author:qs@1.com
 */
@PropertySource("classpath:spring.properties")
@ComponentScan(value="org.example",
                        excludeFilters = {@ComponentScan.Filter(
                                type = FilterType.ASSIGNABLE_TYPE,//FilterType.ANNOTATION,
                                classes = UserService.class//classes = Component.class
                        )})
//@ComponentScan("org.example")
//@Configuration
public class AppConfig {

    /**
     * 国际化
     *
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        // messages.properties那么就得设置为messages
        resourceBundleMessageSource.setBasename("messages");
        return resourceBundleMessageSource;
    }

    @Bean
    public ApplicationListener applicationListener() {
        return new ApplicationListener() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                //if (event.getClass().getName().equals(MyApplicationEvent.class.getName())) {
                if (MyApplicationEvent.class.isAssignableFrom(event.getClass())) {
                    MyApplicationEvent myApplicationEvent = (MyApplicationEvent) event;
                    System.out.println("接收到信息：" + myApplicationEvent.getMsg());
                }
                else if (event instanceof PayloadApplicationEvent){
                    PayloadApplicationEvent payloadApplicationEvent = (PayloadApplicationEvent) event;
                    System.out.println("接收到了payload信息：" + payloadApplicationEvent.getPayload());
                }
                else {
                    System.out.println("接收到了一条消息：" + event.getSource());
                }
            }
        };
    }

    /**
     * spring整合jdk的类型转换
     * @return
     */
//    @Bean
//    public static CustomEditorConfigurer customEditorConfigurer() {
//        CustomEditorConfigurer customEditorConfigurer = new CustomEditorConfigurer();
//        Map<Class<?>, Class<? extends PropertyEditor>> customEditorsMap = new HashMap<>();
//        customEditorsMap.put(User.class, StringToUserPropertyEditor.class);
//
//        customEditorConfigurer.setCustomEditors(customEditorsMap);
//        return customEditorConfigurer;
//    }

    /**
     * 注册spring提供的ConversionService
     * 这个方法的名称还不能改成其他的不然没用哎
     * @return
     */
    @Bean
    public ConversionServiceFactoryBean conversionService() {
        ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();
        //conversionServiceFactoryBean.setConverters(Collections.singleton(new StringToUserConverter()));
        Set<Object> typeConverterSet = new HashSet<>();
        typeConverterSet.add(new StringToUserConverter());
        typeConverterSet.add(new StringToPersonConverter());
        conversionServiceFactoryBean.setConverters(typeConverterSet);

        return conversionServiceFactoryBean;
    }

}
