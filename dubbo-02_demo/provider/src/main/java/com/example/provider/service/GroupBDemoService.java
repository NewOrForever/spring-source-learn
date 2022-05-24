package com.example.provider.service;

import com.example.DemoService;
import org.apache.dubbo.config.annotation.Service;

/**
 * ClassName:GroupDemoService
 * Package:com.example.provider.service
 * Description:
 *
 * @Date:2022/4/25 9:29
 * @Author:qs@1.com
 */
//@Service(interfaceClass = DemoService.class, version = "group", group = "groupB")
public class GroupBDemoService implements DemoService {
    @Override
    public String sayHello(String name) {
        return "---------> hello, group B! " + name;
    }
}
