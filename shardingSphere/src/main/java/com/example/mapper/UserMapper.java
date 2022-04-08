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
    @Select("select u.user_id,u.username,d.uvalue ustatus from t_user u left join t_dict d on u.ustatus = d.ustatus")
    public List<User> queryUserStatus();

    @Select("select u.user_id,u.username,d.uvalue ustatus from t_user u left join t_dict d on u.user_id = d.dict_id")
    public List<User> queryUserStatusById();
}
