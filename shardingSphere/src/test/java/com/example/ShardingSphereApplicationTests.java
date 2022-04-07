package com.example;

import com.example.entity.Course;
import com.example.mapper.CourseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ShardingSphereApplicationTests {

//    @Resource
//    private CourseMapper courseMapper;


    @Test
    void addCourse() {
        System.out.println("test");
//        for (int i = 0; i < 10; i++) {
//            Course c = new Course();
//            c.setCname("java");
//            c.setUserId(1001L);
//            c.setCstatus("1");
//            courseMapper.insert(c);
            //insert into course values ....
//        }
    }

}
