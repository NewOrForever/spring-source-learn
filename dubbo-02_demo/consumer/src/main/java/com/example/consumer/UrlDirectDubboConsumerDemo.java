package com.example.consumer;

import com.example.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@EnableAutoConfiguration
public class UrlDirectDubboConsumerDemo {

    /**
     * 测试消费者直连服务提供者，绕过注册中心（把注册中心关了）
     * 这种一般用于开发测试阶段用（详细可以参考官方文档）
     */

    @Reference(version = "default", group = "default", url = "dubbo://192.168.65.178:20880")
    private DemoService demoService;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(UrlDirectDubboConsumerDemo.class, args);
        DemoService demoService = context.getBean(DemoService.class);
        System.out.println(demoService.sayHello("urlDirect"));
    }

}
