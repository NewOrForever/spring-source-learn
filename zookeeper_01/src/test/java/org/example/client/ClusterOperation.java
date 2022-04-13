package org.example.client;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * ClassName:ClusterOperation
 * Package:org.example.client
 * Description: 集群操作
 *
 * @Date:2022/4/13 11:02
 * @Author:qs@1.com
 */
public class ClusterOperation extends ClusterBase{
    private static final Logger log = LoggerFactory.getLogger(ClusterOperation.class);

    @Test
    public void testReconnect() {
        ZooKeeper zooKeeper = getZooKeeper();
        while (true) {
            try {
                byte[] data = zooKeeper.getData("/reconnect", false, null);
                log.info("get data：{}", new String(data));

                TimeUnit.SECONDS.sleep(15);
            } catch (Exception e) {
                e.printStackTrace();
                log.info("开始重连。。。");
            }
        }
    }

}
