package com.example;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ConsumerApplication {

    @Reference(version = "default", group = "default")
    private DemoService demoService;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ConsumerApplication.class, args);
        DemoService demoService = context.getBean(DemoService.class);
        System.out.println(demoService.sayHello("sq"));
    }

}
