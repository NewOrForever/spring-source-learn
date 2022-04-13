package com.example.controller;

import com.example.service.OrderService;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:TestController
 * Package:com.example.controller
 * Description:
 *
 * @Date:2022/4/13 15:06
 * @Author:qs@1.com
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CuratorFramework curatorFramework;

    @RequestMapping("/reduceStock/{pid}")
    public void reduceStock(@PathVariable Integer pid) {
        try {
            orderService.reduceStock(pid);
        } catch (Exception e) {
            System.out.println("=====================" + e.getMessage());
        }
    }

    @RequestMapping
    public void test() {
        System.out.println("=================111");
    }

}
