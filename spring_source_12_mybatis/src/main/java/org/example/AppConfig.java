package org.example;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.mybatis.spring.MyImportBeanDefinitionRegistrar;
import org.example.mybatis.spring.MyMapperScan;
import org.example.service.User;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.InputStreamResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;

/**
 * ClassName:AppConfig
 * Package:org.example
 * Description:
 *
 * @Date:2022/2/7 16:56
 * @Author:qs@1.com
 */
@ComponentScan("org.example")
//@MyMapperScan("org.example.mapper")
@Configuration
@MapperScan("org.example.mapper")
@EnableTransactionManagement
public class AppConfig {

    /**
     * MapperFactoryBean的父类SqlSessionDaoSupport有setSqlSessionFactory方法
     * 创建MapperFactoryBean这个bean的时候set注入，参数传入set方法并执行set方法创建一个sqlSessionTemplate（这里的sqlSessionProxy这个jdk代理对象挺重要的）
     * InvocationHandler方法拦截器中首先会去拿sqlsession
     * sql执行流程：
     * SqlSessionTemplate.selectOne() -> sqlSessionProxy.selectOne() -> 代理逻辑 -> DefaultSqlSession.selectOne()
     *
     * MapperProxy的sqlsession是sqlsessiontemplate
     * @return
     * @throws IOException
     */
//    @Bean
//    public SqlSessionFactory sqlSessionFactory() throws IOException {
//        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis.xml");
//        // xml解析成Configuration对象
//        // new一个DefaultSqlSessionFactory对象
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
//        return sqlSessionFactory;
//    }

    /**
     * 直接注册MapperScannerConfigurer这个bean的方式来代理@MapperScan注解
     * @Configuration使用时，因为MapperScannerConfigurer是一个BeanDefinitionRegistryPostProcessor，它getBean
     * 比enhance configuration早的缘故（我猜测的啊）-> 所以要使用static方法
     */
//    @Bean
//    public static MapperScannerConfigurer mapperScannerConfigurer() {
//        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//        mapperScannerConfigurer.setBasePackage("org.example.mapper");
//        return mapperScannerConfigurer;
//    }

    /**
     * 测试autowire时子类属性注入的时候属性描述器会不会去拿父类的set方法
     *  - 可以拿到父类的set方法
     *  - Object这个父类的getClass方法必然能拿到的
     * @return
     */
    @Bean(autowire = Autowire.BY_TYPE)
    public User user() {
        return new User();
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

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/boot_user?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        // 不配置这个不会解析mybatis.xml这个配置文件
        // checkDaoConfig会去configuration.addMapper(this.mapperInterface)方法 -> 添加到MapperRegistry
        // sqlSessionFactoryBean.setConfigLocation(new InputStreamResource(Resources.getResourceAsStream("mybatis.xml")));
        return sqlSessionFactoryBean.getObject();
    }


    /**
     * 直接注册DefaultSqlSession来测试不开启事务但是一级缓存生效
      */
//    @Bean
//    public SqlSession sqlSession() throws Exception {
//        // 这里返回的就是DefaultSqlSession
//        return sqlSessionFactory().openSession();
//    }

}
