package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName:UserMapper
 * Package:com.example.mapper
 * Description:
 *
 * @Date:2022/4/8 12:58
 * @Author:qs@1.com
 */
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM user WHERE user_id = #{userId}")
    User selectByUserId(Long userId);
}
