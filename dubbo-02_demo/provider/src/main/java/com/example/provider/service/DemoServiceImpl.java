package com.example.provider.service;

import com.example.DemoService;
import org.apache.dubbo.config.annotation.Service;

/**
 * ClassName:DefaultDemoService
 * Package:com.example.provider.service
 * Description:
 *
 * @Date:2022/4/22 15:45
 * @Author:qs@1.com
 */
@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "hello，" + name;
    }
}
