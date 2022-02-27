package org.example;

import org.example.transactionSynchronization.MyTransactionSynchronization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * ClassName:OrderService
 * Package:org.example
 * Description:
 *
 * @Date:2022/2/23 21:37
 * @Author:qs@1.com
 */
@Component
public class OrderService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 思考：selfReference是没有别的符合的bean了才会匹配自己，那我设置了另外的OrderService的Bean
    // 此时test方法中调用a方法的代理对象和test方法的代理对象不同，那么事务还能正常使用吗？
    @Autowired
    private OrderService orderService;

    @Transactional(propagation = Propagation.SUPPORTS)
    public void test() {
//        TransactionSynchronizationManager.registerSynchronization(new MyTransactionSynchronization());
        jdbcTemplate.execute("insert into users(id,username,password,sex,deleted) values(1,'1','1','1','0')");
        jdbcTemplate.execute("insert into users(id,username,password,sex,deleted) values(3,'1','1','1','0')");
        orderService.a();
    }

    @Transactional
//    @Transactional
    public void a() {
        jdbcTemplate.execute("insert into users(id,username,password,sex,deleted) values(2,'1','1','1','0')");
    }
}
