package org.example.jedis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * ClassName:JedisClusterTest
 * Package:org.example.jedis
 * Description:
 *
 * @Date:2022/3/30 14:00
 * @Author:qs@1.com
 */
public class JedisClusterTest {
    public static void main(String[] args) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(5);

        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        jedisClusterNodes.add(new HostAndPort("192.168.65.228", 8001));
        jedisClusterNodes.add(new HostAndPort("192.168.65.229", 8002));
        jedisClusterNodes.add(new HostAndPort("192.168.65.230", 8003));
        jedisClusterNodes.add(new HostAndPort("192.168.65.228", 8004));
        jedisClusterNodes.add(new HostAndPort("192.168.65.229", 8005));
        jedisClusterNodes.add(new HostAndPort("192.168.65.230", 8006));
        JedisCluster jedisCluster = null;
        try {
            jedisCluster = new JedisCluster(jedisClusterNodes, 6000, 5000, 10, "123456", jedisPoolConfig);

            // connectionTimeout：指的是连接一个url的连接等待时间
            // soTimeout：指的是连接上一个url，获取response的返回等待时间
            System.out.println(jedisCluster.set("cluster", "zhuge"));
            System.out.println(jedisCluster.get("cluster"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedisCluster != null) {
                jedisCluster.close();
            }
        }


    }
}
