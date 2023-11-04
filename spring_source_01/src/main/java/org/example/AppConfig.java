package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * ClassName:AppConfig
 * Package:org.example
 * Description:
 *
 * @Date:2021/12/16 16:41
 * @Author:qs@1.com
 */
@ComponentScan(basePackages = "org.example")
//@EnableAspectJAutoProxy // 切面
@EnableTransactionManagement // 开启事务管理器
@Configuration
public class AppConfig {

    // compnent 和 bean同时去注入一个对象，名称也相同，这时bean会覆盖掉compnent（beandefinitionmap）
    // 当然可以配置不能覆盖，但是设置为不能覆盖后这样是会报错的
    @Bean
    public OrderService orderService() {
        return new OrderService();
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }


    @Bean
    public JdbcTemplate JdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    /**
     * 如果没有@Configuration,JdbcTemplate和transactionManager因为各自都调用了dataSource()方法,因此他们各自获取到的都是一个
     * 新的对象,这就会导致事务不生效
     * 加了@Confuguration后,AppConfig会生成代理对象，由代理对象去调用datasource()方法会去spring容器中找是否存在datasource这个bean，如果
     * 有则获取,没有则去真正的调用这个datasource()方法创建一个新的,因此获取到的datasource是一样的,事务也就生效了
     *
      */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/boot_user?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }


}
