package com.example.controller;

import com.example.common.RedisUtil;
import com.example.model.Product;
import com.example.service.ProductService;
import com.sun.org.apache.xpath.internal.operations.String;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ClassName:ProductController
 * Package:com.example.controller
 * Description:
 *
 * @Date:2022/4/2 9:09
 * @Author:qs@1.com
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/create")
    public Product create(@RequestBody Product product) {
        return productService.create(product);
    }

    @PostMapping("/update")
    public Product update(@RequestBody Product product) {
        return productService.update(product);
    }

    @RequestMapping("/get/{productId}/{map}")
    public Product getProduct(@PathVariable Long productId, @PathVariable Map<String, String> map) {
        return productService.get(productId);
    }

    @RequestMapping("/test")
    public void testRedis() {
        redisTemplate.opsForValue().set("string", "redis");
        redisTemplate.opsForHash().put("hash", "field", "hashvalue");

        System.out.println(redisTemplate.opsForValue().get("string"));
        System.out.println(redisTemplate.opsForHash().get("hash", "field"));
    }

}
