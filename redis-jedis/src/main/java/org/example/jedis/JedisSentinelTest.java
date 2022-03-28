package org.example.jedis;

import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

/**
 * ClassName:JedisSingleTest
 * Package:org.example.jedis
 * Description:
 *
 * @Date:2022/3/28 9:21
 * @Author:qs@1.com
 */
public class JedisSentinelTest {

    public static void main(String[] args) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(5);

        String masterName = "mymaster";
        Set<String> sentinels = new HashSet<String>();
        sentinels.add(new HostAndPort("192.168.65.227",26379).toString());
        sentinels.add(new HostAndPort("192.168.65.227",26380).toString());
        sentinels.add(new HostAndPort("192.168.65.227",26381).toString());
        //JedisSentinelPool其实本质跟JedisPool类似，都是与redis主节点建立的连接池
        //JedisSentinelPool并不是说与sentinel建立的连接池，而是通过sentinel发现redis主节点并与其建立连接
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(masterName, sentinels, jedisPoolConfig, 3000, null);
        Jedis jedis = null;
        try {
            // 从连接池里拿出一个连接执行命令
            jedis = jedisSentinelPool.getResource();
            System.out.println(jedis.set("sentinelTest", "0000001"));
            System.out.println(jedis.get("sentinelTest"));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                // 注意这里不是关闭连接，在JedisPool模式下，Jedis会被归还给资源池。
                jedis.close();
            }
        }


    }
}
