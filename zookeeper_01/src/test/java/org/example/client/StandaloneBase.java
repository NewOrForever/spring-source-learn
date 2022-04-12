package org.example.client;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public abstract class StandaloneBase {
    private static final Logger log = LoggerFactory.getLogger(StandaloneBase.class);

    private static final Integer SESSION_TIMEOUT = 30 * 1000;
    private static final String CONNECT_STR = "192.168.65.227";
    private static ZooKeeper zooKeeper = null;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private Watcher watcher = new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            if (event.getType() == Event.EventType.None &&
                    event.getState() == Event.KeeperState.SyncConnected) {
                log.info("建立连接");
                countDownLatch.countDown();
            }
        }
    };

    @Before
    public void init() throws IOException, InterruptedException {
        // zookeeper的创建是异步的
        zooKeeper = new ZooKeeper(CONNECT_STR, SESSION_TIMEOUT, watcher);
        log.info("连接中。。。");
        countDownLatch.await();
    }

    protected static String getConnectStr() {
        return CONNECT_STR;
    }

    protected static Integer getSessionTimeout() {
        return SESSION_TIMEOUT;
    }

    public static ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    @After
    public void zkSleep(){
        try {
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
