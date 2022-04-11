package com.example.controller;

import com.example.controller.service.CourseService;
import com.example.entity.Course;
import com.example.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName:DynamicFrameDsTestController
 * Package:com.example.controller
 * Description:
 *
 * @Date:2022/4/9 14:09
 * @Author:qs@1.com
 */
@RestController
@RequestMapping("/frame")
public class DynamicFrameDsTestController {

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseService courseService;

    @RequestMapping("/testDynamic")
    public void testDynamic() {
        List<Course> courses = courseMapper.queryAll();
        courses.forEach(System.out::println);

        courses = courseMapper.queryAnotherAll();
        courses.forEach(System.out::println);
    }

    @RequestMapping("/testDynamicUseJdbc")
    public void testDynamicUseJdbc(Course course) {
        List list = courseService.selectCourse();
        list.forEach(System.out::println);

        courseService.createCourse(course);
    }
}
