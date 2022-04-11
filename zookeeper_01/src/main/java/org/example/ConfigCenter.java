package org.example;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:ConfigCenter
 * Package:org.example
 * Description: 模拟一个配置中心
 *
 * @Date:2022/4/11 13:13
 * @Author:qs@1.com
 */
public class ConfigCenter {
    private static final Logger logger =  LoggerFactory.getLogger(ConfigCenter.class);

    private static final String CONNECT_STR = "192.168.65.227:2181";
    private static final Integer SESSION_TIMEOUT = 30 * 1000;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {

        // WatchedEvent state:SyncConnected type:None path:null
        ZooKeeper zooKeeper = new ZooKeeper(CONNECT_STR, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getType() == Event.EventType.None && event.getState() == Event.KeeperState.SyncConnected) {
                    logger.info("连接已建立");
                    countDownLatch.countDown();
                }
            }
        });

        countDownLatch.await();

        zooKeeper.create("/myconfig", new byte[], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)


        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }
}
