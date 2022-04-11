package com.example.controller.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.entity.Course;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName:CourseService
 * Package:com.example.controller.service
 * Description:
 *
 * @Date:2022/4/9 14:21
 * @Author:qs@1.com
 */
@Service
@DS("write")
public class CourseService {
    @Resource
    private JdbcTemplate jdbcTemplate;

    //    @DS("read")
    public List selectCourse(){
        return jdbcTemplate.queryForList("select * from course");
    }

    @DS("read")
    public int createCourse(Course course){
        return jdbcTemplate.update("insert into course values(?,?,?,?)",course.getCid(),course.getCname(),course.getUserId(),course.getCstatus());
    }
}
