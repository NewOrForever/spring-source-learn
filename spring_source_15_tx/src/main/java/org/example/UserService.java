package org.example;

import org.example.transactionSynchronization.MyTransactionSynchronization;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * ClassName:UserService
 * Package:org.example
 * Description:
 *
 * @Date:2022/2/16 21:21
 * @Author:qs@1.com
 */
@Component
public class UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserService userService;

    @Transactional
    public void test() {
        TransactionSynchronizationManager.registerSynchronization(new MyTransactionSynchronization());

//        System.out.println(AopContext.currentProxy());
//        System.out.println(jdbcTemplate.queryForObject("select username from users", String.class));
        jdbcTemplate.execute("insert into users(id,username,password,sex,deleted) values(1,'1','1','1','0')");


        try {
            userService.a();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            userService.a();
//        } catch (Exception e) {
//            // 强制回滚
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//        }
    }

    @Transactional
    public void a() {
//        DefaultTransactionStatus transactionStatus = (DefaultTransactionStatus) TransactionAspectSupport.currentTransactionStatus();
//        DataSourceTransactionObject transaction = (DataSourceTransactionObject) transactionStatus.getTransaction(); // private class
//        jdbcTemplate.execute("insert into users(id,username,password,sex,deleted) values(2,'1','1','1','0')");
//        jdbcTemplate.execute("insert into users(id,username,password,sex,deleted) values(a,'1','1','1','0')");
//        throw new NullPointerException();
    }
}
