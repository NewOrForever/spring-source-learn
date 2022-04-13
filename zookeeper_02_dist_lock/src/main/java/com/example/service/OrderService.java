package com.example.service;

import com.example.entity.Order;
import com.example.entity.Product;
import com.example.mapper.OrderMapper;
import com.example.mapper.ProductMapper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:OrderService
 * Package:com.example.service
 * Description:
 *
 * @Date:2022/4/13 15:07
 * @Author:qs@1.com
 */
@Service
public class OrderService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CuratorFramework curatorFramework;

    private static final String PRODUCT_LOCK_PREFIX = "/product_lock_";

    @Transactional
    public void reduceStock(Integer pid) throws Exception {

        // 创建订单减库存
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, PRODUCT_LOCK_PREFIX + pid);
        try {
            lock.acquire();
            Product product = productMapper.getProduct(pid);
            // 模拟耗时业务
            //sleep(500);

            if (product.getStock() <= 0) {
                throw new RuntimeException("out of stock：" + product.getStock());
            }
            int i = productMapper.deductStock(pid);
            if (i == 1) {
                Order order = new Order();
                order.setPid(pid);
                order.setUserId(UUID.randomUUID().toString());
                orderMapper.insert(order);
                System.out.println("真实库存：" + (product.getStock() - 1));
            } else {
                throw new RuntimeException("deduct stock fail, retry.");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            lock.release();
        }
    }

    public void sleep(long await) {
        try {
            TimeUnit.MILLISECONDS.sleep(await);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
