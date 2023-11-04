package org.example.mapper;

import org.apache.ibatis.annotations.Select;
import org.example.App;
import org.example.entity.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    public User selectById();


    @Select("select  * from users")
    List<Map<String, String>> selectAll();

    @Select("select  * from users")
    List<App.MyUser> selectUsers();
}
