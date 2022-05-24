package org.example.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * ClassName:OrderService
 * Package:org.example.mapper
 * Description:
 *
 * @Date:2022/2/8 8:26
 * @Author:qs@1.com
 */
public interface OrderMapper {

    @Select("SELECT username FROM users WHERE id=1")
    String selectById();
}
