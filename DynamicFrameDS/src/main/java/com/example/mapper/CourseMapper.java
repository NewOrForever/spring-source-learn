package com.example.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Course;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName:CourseMapper
 * Package:com.example.mapper
 * Description:
 *
 * @Date:2022/4/9 10:41
 * @Author:qs@1.com
 */
public interface CourseMapper extends BaseMapper<Course> {
    @DS("read")
    @Select("select * from course")
    public List<Course> queryAll();

    @DS("write")
    @Select("select * from course")
    public List<Course> queryAnotherAll();
}
