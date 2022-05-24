package com.example.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
 * ClassName:ProductService
 * Package:com.example.service
 * Description: 一步步优化redis缓存使用
 * 1. 冷热数据分离 - 增加过期时间，每次从缓存中get到后expire延期
 * 2. 防止缓存击穿：批量上架商品后这批商品同一时间过期 -> 这时大量请求进来访问这些商品缓存中没有 -> 全都去访问数据库导致数据库瞬时压力增大
 *     - 解决方案：过期时间随机 - 基础时间 + 随机时间
 * 3. 防止缓存穿透：恶意攻击不断地刷不存在的商品   ----->redis ---没有--> db ---没有-->  整个就穿透了
 *     - 解决方案：缓存空对象（设置短暂的随机过期时间，防止占用大量的缓存空间）
 * 4. 冷门数据突发性热点缓存重建导致系统压力暴增
 *    案例：商城让大v直播带货（冷门商品）------> 高并发访问冷门商品 -----> 缓存中没有 -----> 高并发直接打到db导致数据库压力暴增
 *    - 解决方案：加分布式锁
 * 5. 缓存与数据库双写不一致
 *    - 解决方案：加锁 - 写的地方要加锁，查数据库后写缓存这两步要加锁
 *    - 优化：使用读写锁优化，读锁可以并发执行提升性能
 *
 * @Date:2022/4/2 9:10
 * @Author:qs@1.com
 */
@Service
public class ProductService {
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
    public Product create(Product product) {
        Product productResult = null;

        // 加分布式锁解决缓存与数据库双写不一致问题
//        RLock productUpdateLock = redisson.getLock(LOCK_PRODUCT_UPDATE_PREFIX + product.getId());
        RReadWriteLock readWriteLock = redisson.getReadWriteLock(LOCK_PRODUCT_UPDATE_PREFIX + product.getId());
        RLock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        try {
            productResult = productDao.create(product);
            redisUtil.set(RedisKeyPrefixConst.PRODUCT_CACHE + product.getId(), JSON.toJSONString(productResult),
                    generateProductCacheTimeOut(), TimeUnit.SECONDS);
        } finally {
            writeLock.unlock();
        }
        return productResult;
    }

    @Transactional
    public Product update(Product product) {
        Product productResult = null;

        // 加分布式锁解决缓存与数据库双写不一致问题
//        RLock productUpdateLock = redisson.getLock(LOCK_PRODUCT_UPDATE_PREFIX + product.getId());
        // 优化（读写锁）：这里是写，用写锁
        RReadWriteLock readWriteLock = redisson.getReadWriteLock(LOCK_PRODUCT_UPDATE_PREFIX + product.getId());
        RLock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        try {
            productResult = productDao.update(product);
            redisUtil.set(RedisKeyPrefixConst.PRODUCT_CACHE + product.getId(), JSON.toJSONString(productResult),
                    generateProductCacheTimeOut(), TimeUnit.SECONDS);
        } finally {
//            productUpdateLock.unlock();
            writeLock.unlock();
        }
        return productResult;
    }

    public Product get(Long productId) {
        Product product = null;
        String productCacheKey = RedisKeyPrefixConst.PRODUCT_CACHE + productId;

        // 第一批并发不走这里，后面的请求都会走这个缓存
        // 所以性能问题不大，90%不会走锁而是在这里并发执行
        product = getProductFromCache(productCacheKey);
        if (product != null) {
            return product;
        }

        // 加分布式锁解决热点缓存并发重建问题
        RLock hotCreateCacheLock = redisson.getLock(LOCK_PRODUCT_HOT_CACHE_CREATE_PREFIX + productId);
        hotCreateCacheLock.lock();
        try {
            product = getProductFromCache(productCacheKey);
            if (product != null) {
                return product;
            }

            // from db
            // 加分布式锁解决缓存与数据库双写不一致问题
//            RLock productUpdateLock = redisson.getLock(LOCK_PRODUCT_UPDATE_PREFIX + productId);
            // 优化（读写锁）：这里是读用读锁 - 并发执行提高性能，写的地方使用写锁（阻塞）
            RReadWriteLock productUpdateLock = redisson.getReadWriteLock(LOCK_PRODUCT_UPDATE_PREFIX + productId);
            RLock readLock = productUpdateLock.readLock();
            readLock.lock();
            try {
                product = productDao.get(productId);
                if (product != null) {
                    redisUtil.set(productCacheKey, JSON.toJSONString(product),
                            generateProductCacheTimeOut(), TimeUnit.SECONDS);
                } else {
                    // 设置空缓存解决缓存穿透问题
                    redisUtil.set(productCacheKey, EMPTY_CACHE, generateEmptyCacheTimeOut(), TimeUnit.SECONDS);
                }
            } finally {
//                productUpdateLock.unlock();
                readLock.unlock();
            }

        } finally {
            hotCreateCacheLock.unlock();
        }

        return product;
    }

    public Integer generateProductCacheTimeOut() {
        // 加随机超时机制解决缓存批量失效(击穿)问题
        return PRODUCT_CACHE_TIMEOUT + new Random().nextInt(5) * 60 * 60;
    }

    public Integer generateEmptyCacheTimeOut() {
        return 60 + new Random().nextInt(30);
    }

    public Product getProductFromCache(String productCacheKey) {
        // 多级缓存查询，jvm级别缓存可以交给单独的热点缓存系统统一维护，有变动推送到各个web应用系统自行更新
        // jvm级别缓存可以抗并发百万级别
        Product product = productMap.get(productCacheKey);
        if (product != null) {
            return product;
        }

        String productJsonFromCache = redisUtil.get(productCacheKey);
        if (StringUtils.hasLength(productJsonFromCache)) {
            if (productJsonFromCache.equals(EMPTY_CACHE)) {
                // 空缓存
                redisUtil.expire(productCacheKey, generateEmptyCacheTimeOut(), TimeUnit.SECONDS);
                // 和前端约定一下这种原始对象数据不显示
                // return null的话有点问题 -> 还会去查DB
                return new Product();
            }

            // 缓存读延期 - 冷热数据分离
            redisUtil.expire(productCacheKey, generateProductCacheTimeOut(), TimeUnit.SECONDS);
            return JSON.parseObject(productJsonFromCache, Product.class);
        }

        return null;
    }

}
