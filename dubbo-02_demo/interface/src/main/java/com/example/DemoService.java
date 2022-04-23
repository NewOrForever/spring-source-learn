package com.example;

import java.util.concurrent.CompletableFuture;

/**
 * ClassName:DemoService
 * Package:com.example
 * Description:
 *
 * @Date:2022/4/22 14:52
 * @Author:qs@1.com
 */
public interface DemoService {

    String sayHello(String name);

    // 异步调用方法
    default CompletableFuture<String> sayHelloAsync(String name) {
        return null;
    };

}
