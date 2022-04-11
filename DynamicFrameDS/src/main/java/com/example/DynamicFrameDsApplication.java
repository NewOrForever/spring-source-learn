package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.mapper")
public class DynamicFrameDsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicFrameDsApplication.class, args);
    }

}
