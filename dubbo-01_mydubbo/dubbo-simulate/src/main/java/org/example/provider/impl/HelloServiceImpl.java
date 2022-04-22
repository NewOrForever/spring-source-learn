package org.example.provider.impl;

import org.apache.dubbo.config.annotation.Service;
import org.example.provider.api.HelloService;

/**
 * ClassName:HelloServiceImpl
 * Package:org.example.provider
 * Description:
 *
 * @Date:2022/4/21 14:47
 * @Author:qs@1.com
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "hello：" + name;
    }
}
