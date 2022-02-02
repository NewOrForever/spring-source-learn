package org.example;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * ClassName:UserService
 * Package:org.example
 * Description:
 *  判断某一对象是否实现某一接口：
 *      对象 instance of InitializingBean
 *
 *
 * @Date:2021/12/16 16:33
 * @Author:qs@1.com
 */
//@Component
public class UserService {//implements InitializingBean {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserService userService;

//    @Autowired
//    private OrderService orderService;

//    public UserService() {
//        System.out.println(1);
//    }

//    public UserService(OrderService orderService12) {
//        this.orderService = orderService12;
//        System.out.println(12);
//    }


//    public UserService(OrderService orderService) {
//        this.orderService = orderService;
//        System.out.println(3);
//    }

//    public UserService(OrderService orderService,  OrderService orderService1) {
//        this.orderService = orderService;
//        System.out.println(3);
//    }

    @Transactional
    public void test() {
     //   System.out.println("test");
        String sql = "INSERT INTO users VALUES(2, '1','1',1,1,NOW(),NOW())";
        jdbcTemplate.execute(sql);
        //throw  new NullPointerException();
        //a(); // 这样写不会报事务已经存在的错误,因为此时调用者不是代理对象,而是目标对象,对目标对象而言事务的注解是没用的
        /**
         * 解决:
         *  方法一: 可以写个类(UserServiceBase),将方法a()放入其中,最后在UserService中注入(注入后获取到的是代理对象(spring会去判断普通对象需不需要Aop))
         *  方法二: 直接在UserService中再注入一个UserService(注入的是代理对象,调用a()方法的时候是会触发事务已存在的错误)
         */
        userService.a();
    }

    // Propagation.NEVER: 已经存在事务,再调用时会报错
    @Transactional(propagation = Propagation.NEVER)
    public void a() {

    }

//    // 初始化前
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("初始化前。。。");
//    }
//
//    @PostConstruct
//    public void postConstr() {
//        System.out.println("初始化前2.。。。");
//    }

}
