package com.example.mapper;
import com.example.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 * ClassName:OrderMapper
 * Package:com.example.mapper
 * Description:
 *
 * @Date:2025/3/25 17:33
 * @Author:qs@1.com
 */

@Mapper
public interface OrderMapper {

    @Insert("INSERT INTO t_order (user_id, product_id, quantity, total_price, create_time) " +
            "VALUES (#{userId}, #{productId}, #{quantity}, #{totalPrice}, #{createTime})")
    int insert(Order order);

    @Select("SELECT * FROM t_order WHERE order_id = #{orderId}")
    Order selectByOrderId(@Param("orderId") Long orderId);

    @Select("SELECT * FROM t_order WHERE user_id = #{userId}")
    List<Order> selectByUserId(@Param("userId") Long userId);

    @Select("SELECT * FROM t_order")
    List<Order> selectAll();
}