package com.example.consumer;

import com.example.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@EnableAutoConfiguration
public class MockDubboConsumerDemo {

    /**
     * 测试服务降级
     *  - mock=force:return+null 表示消费方对该服务的方法调用都直接返回 null 值，不发起远程调用。用来屏蔽不重要服务不可用时对调用方的影响。
     *  - 还可以改为 mock=fail:return+null 表示消费方对该服务的方法调用在失败后，再返回 null 值，不抛异常。用来容忍不重要服务不稳定时对调用方的影响。
     * 测试本地伪装（通常用于服务降级）
     */

    // parameters = {"sayHello.mock", "force:return aaaaa"}
    @Reference(version = "timeout", group = "timeout", timeout = 3000, mock = "force:return RpcException")
    private DemoService demoService;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MockDubboConsumerDemo.class, args);
        DemoService demoService = context.getBean(DemoService.class);
        System.out.println(demoService.sayHello("sq"));
    }

}
