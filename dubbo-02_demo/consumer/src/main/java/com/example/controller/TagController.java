package com.example.controller;

import com.example.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:TagController
 * Package:com.example.controller
 * Description: 使用标签路由来让测试账号使用测试环境，正式账号使用正式环境
 *  - 在拦截器中处理tag
 *  - 服务端开了两个dubbo协议，不同的端口号
 *  - dubbo后台设置标签路由规则
 *
 * @Date:2022/4/25 19:11
 * @Author:qs@1.com
 */
@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private DemoService demoService;

    @RequestMapping("/test")
    public void testTag() {
        System.out.println(demoService.sayHello("测试tag"));
    }
}
