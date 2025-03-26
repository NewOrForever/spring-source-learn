package com.example.controller;

import com.example.entity.Order;
import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * ClassName:OrderController
 * Package:com.example.controller
 * Description:
 *
 * @Date:2025/3/26 9:20
 * @Author:qs@1.com
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public int createOrder(@RequestParam Long userId, @RequestParam Long productId,
                           @RequestParam Integer quantity, @RequestParam BigDecimal totalPrice) {
        return orderService.createOrder(userId, productId, quantity, totalPrice);
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping("/generate")
    public String generateTestData() {
        orderService.generateTestData();
        return "Test data generated successfully!";
    }
}
