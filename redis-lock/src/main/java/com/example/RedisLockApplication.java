package com.example;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RedisLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisLockApplication.class, args);
    }

    @Bean
    public Redisson redisson() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.65.227:6379").setDatabase(0);
        // Redisson的构造方法是protected
        // createConnectionManager  -> id是一个UUID
        return (Redisson) Redisson.create(config);
    }

}
