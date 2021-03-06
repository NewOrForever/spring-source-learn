package com.example;

import org.redisson.Redisson;
import org.redisson.RedissonLock;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:IndexController
 * Package:com.example
 * Description: redis锁
 *
 * @Date:2022/3/28 14:54
 * @Author:qs@1.com
 */
@RestController
@RequestMapping("/index")
public class IndexController implements ApplicationContextAware {

    public static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private Redisson redisson;


    /**
     * 分布式场景下使用jvm的自带的锁synchronized
     * - 很明显这把锁没法锁分布式场景，因为这个是jvm级别的，现在有两个jvm
     */
    @RequestMapping("/jvm_deduct_stock")
    public void jvmDeductStock() {
//        String lockKey = "lock:product:10086";
        synchronized (this) {
            int stock = Integer.parseInt(Objects.requireNonNull(stringRedisTemplate.opsForValue().get("stock")));
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("扣减成功，剩余库存：" + realStock);
            } else {
                System.out.println("扣减失败，库存不足");
            }
        }

    }

    @RequestMapping("/redis_deduct_stock")
    public String redisDeductStock() {
        String lockKey = "lock:product:10086";
        String clientId = UUID.randomUUID().toString();
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, clientId, 10, TimeUnit.MILLISECONDS);
        if (!result) {
            return "error_code";
        }

        try {
            String stockVal = stringRedisTemplate.opsForValue().get("stock");
            int stock = Integer.parseInt(stockVal == null ? "0" : stockVal);
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("扣减成功，剩余库存：" + realStock);
            } else {
                System.out.println("扣减失败，库存不足");
            }
        } finally {
            // 10s过期后，别的线程拿到锁，然后当前线程去释放锁就会把别的线程的锁给释放掉
            // 所以需要确认当前这把锁是不是当前线程的 -> 加锁的时候给锁赋值uuid
            if (clientId.equals(stringRedisTemplate.opsForValue().get(lockKey))) {
                stringRedisTemplate.delete(lockKey);
            }
        }

        return "end";
    }

    @RequestMapping("/redisson_deduct_stock")
    public String redissonDeductStock() {
        String lockKey = "lock:product:10086";
      /*  String clientId = UUID.randomUUID().toString();
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, clientId, 10, TimeUnit.MILLISECONDS);
        if (!result) {
            return "error_code";
        }*/

        // 获取锁对象
        // 初始化对象看一下哦，好多初始化数据都在里面
        RLock redissonLock = redisson.getLock(lockKey);
        // 加分布式锁
        redissonLock.lock();

        // redissonLock.tryLock(); 接口有描述tryLock()的用法

        try {
            String stockVal = stringRedisTemplate.opsForValue().get("stock");
            int stock = Integer.parseInt(stockVal == null ? "0" : stockVal);
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("扣减成功，剩余库存：" + realStock);
            } else {
                System.out.println("扣减失败，库存不足");
            }
        } finally {
            // 10s过期后，别的线程拿到锁，然后当前线程去释放锁就会把别的线程的锁给释放掉
            // 所以需要确认当前这把锁是不是当前线程的 -> 加锁的时候给锁赋值uuid
            /*if (clientId.equals(stringRedisTemplate.opsForValue().get(lockKey))) {
                stringRedisTemplate.delete(lockKey);
            }*/

            // 释放分布式锁
            // 对于重入锁（一个线程加个几次锁？-> 需要unlock几次）
            redissonLock.unlock();
        }

        return "end";
    }


    @RequestMapping("testRedis")
    public void testRedis() {
        stringRedisTemplate.opsForValue().set("testRedis", "001");
        System.out.println(stringRedisTemplate.opsForValue().get("testRedis"));
    }

    @GetMapping("/test")
    public String test() {
        System.out.println(request.getRequestURL());
        System.out.println(request.getRequestURI());
        System.out.println(request.getContextPath());
        System.out.println(request.getServletPath());
        System.out.println(request.isAsyncSupported());
        return "001";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Object aaa = applicationContext.getBean("redisConverter");

    }
}
