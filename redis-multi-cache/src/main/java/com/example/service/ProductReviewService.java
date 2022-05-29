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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * 回顾（重新写了）一下
 *
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
        // 双写不一致、读写不一致，加锁
        RReadWriteLock readWriteLock = redisson.getReadWriteLock(LOCK_PRODUCT_UPDATE_PREFIX + product.getId());
        RLock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        try {
            productResult = productDao.update(product);
            redisUtil.set(RedisKeyPrefixConst.PRODUCT_CACHE + product.getId(), JSON.toJSONString(productResult),
                    generateProductCacheTimeOut(), TimeUnit.SECONDS);
        } finally {
            writeLock.unlock();
        }
        return productResult;
    }


    public Product get(Long productId) {
        String productCacheKey = RedisKeyPrefixConst.PRODUCT_CACHE + productId;
        Product product = getProductFromCache(productCacheKey);
        if (product != null) {
            return product;
        }

        RLock proHotCacheCreateKey = redisson.getLock(LOCK_PRODUCT_HOT_CACHE_CREATE_PREFIX + productId);
        proHotCacheCreateKey.lock();

        try {
            product = getProductFromCache(productCacheKey);
            if (product != null) {
                return product;
            }
            // 双写不一致、读写不一致，加锁
            RReadWriteLock readWriteLock = redisson.getReadWriteLock(LOCK_PRODUCT_UPDATE_PREFIX + productId);
            RLock readLock = readWriteLock.readLock();
            readLock.lock();
            try {
                // from db
                product = productDao.get(productId);
                // cache
                if (product != null) {
                    redisUtil.set(productCacheKey, JSON.toJSONString(product), generateProductCacheTimeOut(), TimeUnit.SECONDS);
                } else {
                    redisUtil.set(productCacheKey, EMPTY_CACHE, generateEmptyCacheTimeOut(), TimeUnit.SECONDS);
                }
            } finally {
                readLock.unlock();
            }
        } finally {
            proHotCacheCreateKey.unlock();
        }

        return product;
    }

    private Integer generateEmptyCacheTimeOut() {
        return 60 + new Random().nextInt(30);
    }

    private Integer generateProductCacheTimeOut() {
        return PRODUCT_CACHE_TIMEOUT + new Random().nextInt(5) * 60 * 60;
    }

    private Product getProductFromCache(String key) {
        String productJsonStr = redisUtil.get(key);
        if (StringUtils.hasLength(productJsonStr)) {
            // 空缓存防止缓存穿透
            if (EMPTY_CACHE.equals(productJsonStr)) {
                redisUtil.expire(key, generateEmptyCacheTimeOut(), TimeUnit.SECONDS);
                return new Product();
            }
            // 读到了就进行延期，冷热分离
            redisUtil.expire(key, generateProductCacheTimeOut(), TimeUnit.SECONDS);
            return JSON.parseObject(productJsonStr, Product.class);
        }
        return null;
    }


}
