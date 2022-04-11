package com.example;

import com.example.config.DynamicDataSource;
import com.example.mapper.CourseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SpringBootTest
class DynamicDataSourceApplicationTests {

    @Test
    public void dynamicTest(){

//        DynamicDataSource.name.set(DynamicDataSource.DATASOURCE_READ_LOOKUP_KEY);
//
//        List<Map<String, Object>> list = courseMapper.selectMaps(null);
//
//        list.forEach(map -> {
////            for (Map.Entry<String, Object> entryset : map.entrySet()) {
////
////            }
////            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
////            while (iterator.hasNext()) {
////                Map.Entry<String, Object> entry = iterator.next();
////            }
//
//            map.forEach((key,value) -> {
//                System.out.println(key + "->" + value);
//            });
//        });


    }

    @Test
    void contextLoads() {
        System.out.println("test");
    }

}
