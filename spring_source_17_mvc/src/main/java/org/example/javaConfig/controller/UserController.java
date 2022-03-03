package org.example.javaConfig.controller;

import org.example.javaConfig.pojo.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * ClassName:UserController
 * Package:org.example.javaConfig.controller
 * Description:
 *
 * @Date:2022/3/3 8:57
 * @Author:qs@1.com
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/save")
    public void save() {
        System.out.println("org.example.javaConfig.controller.UserController#save");
    }

    @RequestMapping("/mapping")
    public ModelAndView mapping() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("a");
        modelAndView.addObject("source", "mapping");
        return modelAndView;
    }

    @RequestMapping("/getUser")
    public User getUser() {
        User user = new User();
        user.setId(1);
        user.setName("test001");
        user.setBirth(new Date());
        return user;
    }


}
