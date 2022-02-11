package org.example.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * ClassName:UserMapper
 * Package:org.example
 * Description:
 *
 * @Date:2022/2/7 16:00
 * @Author:qs@1.com
 */

public interface UserMapper {

    @Select("select 'user'")
    String selectById();
}
