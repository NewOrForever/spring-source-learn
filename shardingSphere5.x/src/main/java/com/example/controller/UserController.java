package com.example.controller;

import com.example.entity.Order;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * ClassName:OrderController
 * Package:com.example.controller
 * Description:
 *
 * @Date:2025/3/26 9:20
 * @Author:qs@1.com
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;


    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return userMapper.selectByUserId(userId);
    }

    @GetMapping("/create")
    public int createUser(@RequestParam Long userId, @RequestParam String userName) {
        User user = new User();
        user.setUserId(userId);
        user.setUsername(userName);
        return userMapper.insert(user);
    }

}
