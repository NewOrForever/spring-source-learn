package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Course;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName:CourseMapper
 * Package:com.example
 * Description:
 *
 * @Date:2022/4/7 9:47
 * @Author:qs@1.com
 */
public interface CourseMapper extends BaseMapper<Course> {
    @Select("select * from course")
    public List<Course> queryAllCourse();
}
