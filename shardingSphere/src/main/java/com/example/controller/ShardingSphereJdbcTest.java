package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Course;
import com.example.mapper.CourseMapper;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName:ShardingSphereJdbcTest
 * Package:com.example.controller
 * Description:
 *
 * @Date:2022/4/7 10:49
 * @Author:qs@1.com
 */
@RestController
@RequestMapping("/jdbc")
public class ShardingSphereJdbcTest {

    @Autowired
    private CourseMapper courseMapper;

    @RequestMapping("/addCourse")
    public void addCourse() {
        for (int i = 0; i < 10; i++) {
            Course c = new Course();
            c.setCname("java");
            c.setUserId(1000L + i);
            c.setCstatus("1");
            courseMapper.insert(c);
            // insert into course values ....
        }
    }

    @RequestMapping("/queryCourse")
    public void queryCourse() {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("cid", 718780650705915905L);
//        List<Course> courses = courseMapper.selectList(queryWrapper);
        List<Course> courses = courseMapper.queryAllCourse();

        courses.forEach(System.out::println);
    }

    @RequestMapping("/queryCourseOrder")
    public void queryCourseOrder() {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("cid");
        List<Course> courses = courseMapper.selectList(queryWrapper);

        courses.forEach(System.out::println);
    }

    // inline策略，对于这种排序查询，也必须要带上cid的查询条件，否则解析出来的实际SQL就会出问题。
    @RequestMapping("/queryCourseOrderWithPk")
    public void queryCourseOrderWithPk() {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("user_id");
        // 排序需要加上分片键的查询条件否则就是course_1、course_2内排序，整个数据不排序，需要我们自己对结果排序
        queryWrapper.eq("cid", 718810636154310657L);
        List<Course> courses = courseMapper.selectList(queryWrapper);

        courses.forEach(System.out::println);
    }

    @RequestMapping("/queryCourseRange")
    public void queryCourseRange(){
        //select * from course where cid between xxx and xxx
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.between("cid",718825382190518273L,728825382190518273L);
        List<Course> courses = courseMapper.selectList(wrapper);
        courses.forEach(course -> System.out.println(course));
    }

    //复杂分片策略，所有查询条件只能有一个统一的类型。
    @RequestMapping("/queryCourseComplex")
    public void queryCourseComplex(){
        QueryWrapper<Course> wrapper = new QueryWrapper<Course>();
        wrapper.orderByDesc("user_id");
        wrapper.in("cid",718825382190518273L,718825382203101184L,718825382219878400L);
        wrapper.between("user_id",1000L,1500L);
//        wrapper.and(courseQueryWrapper -> courseQueryWrapper.between("user_id","3","8"));
        List<Course> course = courseMapper.selectList(wrapper);
        System.out.println(course);
    }

    //强制路由策略。脱离SQL自己指定分片策略。
    /**
     * 测试：HintManager去掉
     * 测试：database策略使用hint
     */
    @RequestMapping("/queryCourseByHint")
    public void queryCourseByHint(){
        //强制只查course_1表
        HintManager hintManager = HintManager.getInstance();
        //注意这两个属性，dataSourceBaseShardingValue用于强制分库
        // 强制查m1数据源
        hintManager.addDatabaseShardingValue("course","1");
        // 强制查course_1表
        hintManager.addTableShardingValue("course","1");

        List<Course> courses = courseMapper.selectList(null);
        courses.forEach(course -> System.out.println(course));
        //线程安全，所有用完要注意关闭。
        hintManager.close();
    }

}
