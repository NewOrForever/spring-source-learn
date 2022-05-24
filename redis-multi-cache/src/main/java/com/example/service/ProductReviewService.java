package com.example.service;

import com.alibaba.fastjson.JSON;
import com.example.common.RedisKeyPrefixConst;
import com.example.common.RedisUtil;
import com.example.dao.ProductDao;
import com.example.model.Product;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 回顾一下
 * @Date:2022/5/24 17:10
 * @Author:qs@1.com
 */
@Service
public class ProductReviewService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private Redisson redisson;

    private static final Integer PRODUCT_CACHE_TIMEOUT = 60 * 60 * 24;
    private static final String EMPTY_CACHE = "{}";
    private static final String LOCK_PRODUCT_HOT_CACHE_CREATE_PREFIX = "lock:product:hot_cache_create:";
    private static final String LOCK_PRODUCT_UPDATE_PREFIX = "lock:product:update:";
    private static Map<String, Product> productMap = new HashMap<>();

    @Transactional
    public Product update(Product product) {
        Product productResult = null;
        try {
            productResult = productDao.update(product);
            redisUtil.set(RedisKeyPrefixConst.PRODUCT_CACHE + product.getId(), JSON.toJSONString(productResult),
                    generateProductCacheTimeOut(), TimeUnit.SECONDS);
        } finally {

        }
        return productResult;
    }


    public Product get(Long productId) {
        String productCacheKey = "lock:product:" + productId;
        redisUtil.get(productCacheKey);
        return null;
    }

    private Integer generateProductCacheTimeOut() {
        return PRODUCT_CACHE_TIMEOUT + new Random().nextInt(5) * 60 * 60;
    }


}
