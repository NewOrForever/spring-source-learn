package org.example.client;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
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

    /**
     * 测试重连
     * 注意啊linux的端口要开啊
     * @throws InterruptedException
     */
    @Test
    public void testReconnect() throws InterruptedException {
        // ZooKeeper zooKeeper = getZooKeeper();
        while (true) {
            try {
                Stat stat = new Stat();
                byte[] data = getZooKeeper().getData("/reconnect", false, stat);
                log.info("get data：{}", new String(data));

                // 这个等待时间开始设置的5s -> 太长，在sleep期间客户端已经完成了重连（服务端发送事件回来监听到然后发起重连）
                // -> 1s能够进入到异常逻辑
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
                log.info("开始重连。。。");

                while (true) {
                    log.info("========zookeeper status: {}", getZooKeeper().getState().name());
                    if (getZooKeeper().getState().isConnected()) {
                        break;
                    }
                    TimeUnit.SECONDS.sleep(3);
                }
            }
        }
    }

}
