package com.example.service;

import com.example.entity.Order;
import com.example.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * ClassName:OrderService
 * Package:com.example.service
 * Description:
 *
 * @Date:2025/3/26 9:11
 * @Author:qs@1.com
 */



@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    public int createOrder(Long userId, Long productId, Integer quantity, BigDecimal totalPrice) {
        Order order = new Order();
        order.setUserId(userId);
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setTotalPrice(totalPrice);
        order.setCreateTime(LocalDateTime.now());
        return orderMapper.insert(order);
    }

    public Order getOrderById(Long orderId) {
        return orderMapper.selectByOrderId(orderId);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderMapper.selectByUserId(userId);
    }

    public List<Order> getAllOrders() {
        return orderMapper.selectAll();
    }

    // 方便测试生成一些订单数据
    public void generateTestData() {
        Random random = new Random();
        for (int i = 0; i < 200; i++) {
            long userId = random.nextInt(100);
            long productId = random.nextInt(50);
            int quantity = random.nextInt(5) + 1;
            BigDecimal totalPrice = new BigDecimal("10.50");
            createOrder(userId, productId, quantity, totalPrice);
        }
    }
}