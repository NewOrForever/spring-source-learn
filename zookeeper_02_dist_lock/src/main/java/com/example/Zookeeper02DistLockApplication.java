package com.example;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.example.mapper")
@EnableTransactionManagement
public class Zookeeper02DistLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(Zookeeper02DistLockApplication.class, args);
    }


    @Bean(initMethod = "start")
    public CuratorFramework curatorFramework() {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(5000, 30);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("192.168.65.227:2181")
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();
        return client;
    }

}
