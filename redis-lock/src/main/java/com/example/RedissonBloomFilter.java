package com.example;

import io.lettuce.core.RedisClient;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * ClassName:RedissonBloomFilter
 * Package:com.example
 * Description:
 *
 * @Date:2022/4/3 15:34
 * @Author:qs@1.com
 */
public class RedissonBloomFilter {
    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.65.227:6379").setDatabase(0);
        RedissonClient redisson = Redisson.create(config);

        RBloomFilter<Object>  bloomFilter = redisson.getBloomFilter("nameList");
        // 初始化布隆过滤器：预计元素为100000000L，误差率为3%，根据这两个参数会计算出底层的bit数组大小
        bloomFilter.tryInit(100000000L, 0.03);
        // 向布隆过滤器插入数据
        bloomFilter.add("zhuge");
        bloomFilter.add("tuling");

        System.out.println(bloomFilter.contains("guojia"));
        System.out.println(bloomFilter.contains("baiqi"));
        System.out.println(bloomFilter.contains("zhuge"));

    }
}
