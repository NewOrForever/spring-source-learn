package com.example.consumer;

import com.example.ConsumerApplication;
import com.example.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@EnableAutoConfiguration
public class TimeoutDubboConsumerDemo {

    /**
     * 用group来区分一个服务接口的多种实现
     * 不知道为啥一个接口的多个实现使用了group也没法注册进去，后续再看什么问题吧
     */

    // 服务端sleep了6s，消费端超时报错
    // 服务端设置了timeout=5s，超时不报错，抛warnning
    @Reference(version = "timeout", group = "timeout", timeout = 3000)
    private DemoService demoService;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TimeoutDubboConsumerDemo.class, args);
        DemoService demoService = context.getBean(DemoService.class);
        demoService.sayHello("sq");
    }

}
