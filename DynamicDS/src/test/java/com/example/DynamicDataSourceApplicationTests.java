package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class DynamicDataSourceApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void dynamicTest(){
        
    }

    @Test
    void contextLoads() {
        System.out.println("test");
    }

}
