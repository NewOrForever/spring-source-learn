package org.example;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * ClassName:UserService
 * Package:org.example
 * Description:
 *
 * @Date:2021/12/23 14:05
 * @Author:qs@1.com
 */
@Component
public class UserService implements ApplicationContextAware {

    /**
     * 使用ApplicationContextAware
     * 在进入初始化后（后置处理器）时，ApplicationContextAwareProcessor会将applicatioContext注入进来
     */
    private ApplicationContext applicationContext;

    @Value("testTypeConverter")
    private User user;
    @Value("100")
    private Person person;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public UserService() {
        System.out.println("init construct");
    }

    public void test() {
        //System.out.println("userservice的test方法国际：" + applicationContext.getMessage("test", null, new Locale("CN")));
        applicationContext.publishEvent("hahaha");

        // 自定义applicationevent
        MyApplicationEvent<String> myApplicationEvent = new MyApplicationEvent<String>(applicationContext,
                new MyDefaultEventType(), "hahahhhh");
        applicationContext.publishEvent(myApplicationEvent);

        System.out.println("test");
    }

    public void typeConvert() {
        System.out.println(user);
        System.out.println(person);
    }

    public class UserServiceInner{}


}
