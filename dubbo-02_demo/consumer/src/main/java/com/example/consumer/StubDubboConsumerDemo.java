package com.example.consumer;

import com.example.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@EnableAutoConfiguration
public class StubDubboConsumerDemo {

    /**
     * 测试本地存根
     *  - 这里的demoService注入的是DemoServiceStub对象 ---> DemoServiceStub对象里面真正引用了远程代理对象
     */
    @Reference(version = "default", group = "default", stub = "true")
    private DemoService demoService;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(StubDubboConsumerDemo.class, args);
        DemoService demoService = context.getBean(DemoService.class);
        System.out.println(demoService.sayHello("stub"));
    }

}
