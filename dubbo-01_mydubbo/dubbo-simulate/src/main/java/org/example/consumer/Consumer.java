package org.example.consumer;

import org.example.framework.ProxyFactory;
import org.example.provider.api.HelloService;

/**
 * ClassName:Consumer
 * Package:org.example.consumer
 * Description:
 *
 * @Date:2022/4/21 16:53
 * @Author:qs@1.com
 */
public class Consumer {
    public static void main(String[] args) {
        // jdk动态代理代理的是接口
        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        String res = helloService.sayHello("sq");
        System.out.println(res);
    }
}
