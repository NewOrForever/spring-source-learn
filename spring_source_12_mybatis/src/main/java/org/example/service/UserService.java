package org.example.service;

import org.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ClassName:UserService
 * Package:org.example.service
 * Description:
 *
 * @Date:2022/2/7 16:22
 * @Author:qs@1.com
 */
@Component
public class UserService {
    @Autowired
    private UserMapper userMapper; // Mybatis UserMapper代理对象-->Bean

    public void test() {
        System.out.println(userMapper.selectById());
    }
}
