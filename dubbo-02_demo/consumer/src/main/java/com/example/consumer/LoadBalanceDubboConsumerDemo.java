package com.example.consumer;

import com.example.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@EnableAutoConfiguration
public class LoadBalanceDubboConsumerDemo {

    /**
     * 负载均衡
     */

    @Reference(version = "default", group = "default", loadbalance = "consistenthash")
    private DemoService demoService;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LoadBalanceDubboConsumerDemo.class, args);
        DemoService demoService = context.getBean(DemoService.class);

        for (int i = 0; i < 10; i++) {
            // 一致性hash：相同参数的请求发到统一提供者
            System.out.println(demoService.sayHello(i % 5 + "loadbalance"));
        }

    }

}
