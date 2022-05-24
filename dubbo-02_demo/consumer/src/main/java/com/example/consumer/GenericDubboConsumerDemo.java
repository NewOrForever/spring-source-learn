package com.example.consumer;

import com.example.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@EnableAutoConfiguration
public class GenericDubboConsumerDemo {

    /**
     * 泛化接口测试：
     *  - 泛化接口调用方式主要用于客户端没有 API 接口及模型类元的情况
     */

    @Reference(id = "demoService", interfaceName = "com.example.DemoService", version = "default", group = "default", generic = true)
    private GenericService genericService;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(GenericDubboConsumerDemo.class, args);
        GenericService genericService = (GenericService) context.getBean("demoService");
        System.out.println(genericService.$invoke("sayHello", new String[]{String.class.getName()}, new Object[]{"generic"}));
    }

}
