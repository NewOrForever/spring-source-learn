package org.example.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:PreHotJedisPoolResourceTest
 * Package:org.example.jedis
 * Description:
 *
 * @Date:2022/5/25 9:31
 * @Author:qs@1.com
 */
public class PreHotJedisPoolResourceTest {

    private static JedisPoolConfig jedisPoolConfig = null;
    private static JedisPool pool = null;

    static {
        initJedisPool();
        preHotJedisPool();
    }

    private static void preHotJedisPool() {
        List<Jedis> minIdleJedisList = new ArrayList<Jedis>(jedisPoolConfig.getMinIdle());

        for (int i = 0; i < jedisPoolConfig.getMinIdle(); i++) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                minIdleJedisList.add(jedis);
                jedis.ping();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                //注意，这里不能马上close将连接还回连接池，否则最后连接池里只会建立1个连接。。
                //jedis.close();
            }
        }

        //统一将预热的连接还回连接池
        for (int i = 0; i < jedisPoolConfig.getMinIdle(); i++) {
            Jedis jedis = null;
            try {
                jedis = minIdleJedisList.get(i);
                //将连接归还回连接池
                jedis.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
            }
        }

    }

    private static JedisPool initJedisPool() {
        jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(5);

        if (pool == null) {
            // timeout，这里既是连接超时又是读写超时，从jedis2.8开始有区分connectionTimeout和soTimeout（socketTimeout）的构造函数
            pool = new JedisPool(jedisPoolConfig, "192.168.65.227", 6379, 3000, null);
        }
        return pool;
    }

    public static void main(String[] args) {
        System.out.println("看pool对象中的allObjects有几个");
        Jedis jedis = pool.getResource();
        System.out.println("这个对象是不是pool里面预热的，还是新建的？" + jedis);
    }

}
