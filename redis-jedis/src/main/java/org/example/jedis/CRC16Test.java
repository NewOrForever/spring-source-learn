package org.example.jedis;

import redis.clients.jedis.util.JedisClusterCRC16;

/**
 * ClassName:CRC16Test
 * Package:org.example.jedis
 * Description:
 *
 * @Date:2022/3/30 15:04
 * @Author:qs@1.com
 */
public class CRC16Test {
    public static void main(String[] args) {
        System.out.println(JedisClusterCRC16.getCRC16("zhuge") & (16384 - 1));
        System.out.println(JedisClusterCRC16.getCRC16("zhuge") % 16384);
    }
}
