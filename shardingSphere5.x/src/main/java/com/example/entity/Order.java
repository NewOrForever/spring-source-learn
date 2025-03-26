package com.example.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ClassName:Order
 * Package:com.example.entity
 * Description:
 *
 * @Date:2025/3/25 17:32
 * @Author:qs@1.com
 */

@Data
public class Order {
    private Long orderId;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private BigDecimal totalPrice;
    private LocalDateTime createTime;
}
