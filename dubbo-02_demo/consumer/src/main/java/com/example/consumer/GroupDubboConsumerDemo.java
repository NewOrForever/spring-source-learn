package com.example.consumer;

import com.example.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@EnableAutoConfiguration
public class GroupDubboConsumerDemo {

    /**
     * 测试分组group
     *  - 没跑起来，不知道啥问题，后续学习之后再看把
     */
    @Reference(interfaceClass = DemoService.class, version = "group", group = "groupA", id = "groupADemoService")
    private DemoService groupADemoService;
    @Reference(interfaceClass = DemoService.class, version = "group", group = "groupB", id = "groupBDemoService")
    private DemoService groupBDemoService;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(GroupDubboConsumerDemo.class, args);
        DemoService groupADemoService = (DemoService) context.getBean("groupADemoService");
        DemoService groupBDemoService = (DemoService) context.getBean("groupBDemoService");
        System.out.println(groupADemoService.sayHello("xxxxx"));
        System.out.println(groupBDemoService.sayHello("aaaaaaa"));
    }

}
