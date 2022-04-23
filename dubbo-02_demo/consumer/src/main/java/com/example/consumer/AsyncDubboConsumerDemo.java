package com.example.consumer;

import com.example.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.CompletableFuture;

@EnableAutoConfiguration
public class AsyncDubboConsumerDemo {

    @Reference(version = "async")
    private DemoService demoService;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AsyncDubboConsumerDemo.class, args);
        DemoService demoService = context.getBean(DemoService.class);
        // 调用直接返回CompletableFuture
        CompletableFuture<String> future = demoService.sayHelloAsync("异步调用");  // 5

        future.whenComplete((v, t) -> {
            if (t != null) {
                t.printStackTrace();
            } else {
                System.out.println("Response: " + v);
            }
        });

        System.out.println("结束了");
    }

}
