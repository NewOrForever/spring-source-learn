package org.example.service;

import org.apache.ibatis.session.SqlSession;
import org.example.mapper.OrderMapper;
import org.example.mapper.UserMapper;
import org.example.mybatis.spring.MyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Autowired
    private OrderMapper orderMapper;

//    @Autowired
//    private SqlSession sqlSession;

    @Transactional
    public void test() {
//        System.out.println((String) sqlSession.selectOne("org.example.mapper.UserMapper.selectById"));
//        System.out.println((String) sqlSession.selectOne("org.example.mapper.UserMapper.selectById"));

        System.out.println(userMapper.selectById());
        System.out.println(userMapper.selectById());
        System.out.println(orderMapper.selectById());
    }

    @Transactional
    public void testTran() {
        System.out.println(userMapper.selectById());
        System.out.println(userMapper.selectById());
        System.out.println(orderMapper.selectById());
    }

    public void testUnTran() {
        System.out.println(userMapper.selectById());
        System.out.println(userMapper.selectById());
        System.out.println(orderMapper.selectById());
    }
}
