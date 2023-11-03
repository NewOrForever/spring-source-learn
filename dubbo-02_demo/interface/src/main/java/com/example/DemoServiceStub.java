package com.example;

import com.sun.org.slf4j.internal.LoggerFactory;

import java.util.logging.Logger;

/**
 * ClassName:DemoServiceStub
 * Package:com.example
 * Description:
 *
 * @Date:2022/4/24 13:17
 * @Author:qs@1.com
 */
public class DemoServiceStub implements DemoService {

    private final DemoService demoService;

    // 构造函数传入真正的远程代理对象
    // 构造方法注入
    public DemoServiceStub(DemoService demoService) {
        this.demoService = demoService;
    }

    @Override
    public String sayHello(String name) {
        System.out.println("before execute remote service, parameter: " + name);
        // 此代码在客户端执行, 你可以在客户端做ThreadLocal本地缓存，或预先验证参数是否合法，等等
        try {
            String result = demoService.sayHello(name);
            System.out.println("after execute remote service, result: " + result);
            return result;
        } catch (Exception e) {
            System.out.println("fail to execute service");
            // 你可以容错，可以做任何AOP拦截事项
            return "容错数据";
        }
    }
}
