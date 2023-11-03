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
     * leastactive：最少活跃调用数，在消费端进行统计，消费者会缓存提供者的信息active
     * 为啥要在消费端统计？我猜想：如果在提供者端统计的话，每次消费者端都要请求到provider去拿active从而来判断出要去请求哪台机器，网络io开销较大
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
