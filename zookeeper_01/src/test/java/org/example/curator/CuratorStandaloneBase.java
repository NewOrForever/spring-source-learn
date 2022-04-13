package org.example.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;
import org.example.client.StandaloneBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * ClassName:CuratorStandaloneBase
 * Package:org.example.client.curator
 * Description: Curator客户端使用
 *
 * @Date:2022/4/12 14:49
 * @Author:qs@1.com
 */
public abstract class CuratorStandaloneBase {
    private static final Logger log = LoggerFactory.getLogger(StandaloneBase.class);

    private static final Integer SESSION_TIMEOUT_MS = 60 * 1000;
    private static final Integer CONNECTION_TIMEOUT_MS = 5000;
    private static final String CONNECT_STR = "192.168.0.110";

    private static CuratorFramework curatorFramework = null;

    @Before
    public void init() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(5000, 30);
        curatorFramework = CuratorFrameworkFactory.builder().connectString(CONNECT_STR)
                .retryPolicy(retryPolicy)                                                           // 重试策略
                .sessionTimeoutMs(SESSION_TIMEOUT_MS)                        // 会话超时时间
                .connectionTimeoutMs(CONNECTION_TIMEOUT_MS)         // 连接超时时间
                .canBeReadOnly(true)                                                             // 该台机器和其他半数以上机器断开后，该机器可以读
                //.namespace("/base")                                                            // 包含隔离名称
                .build();
        curatorFramework.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
                if (connectionState == ConnectionState.CONNECTED) {
                    log.info("连接成功！");
                }
            }
        });

        log.info("连接中。。。");
        curatorFramework.start();
    }

    public void createIfNeed(String path) {
        try {
            Stat stat = curatorFramework.checkExists().forPath(path);
            if (stat == null) {
                String s = curatorFramework.create().forPath(path);
                log.info("path {} created!", s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @After
    public void zkSleep() {
        try {
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static CuratorFramework getCuratorFramework() {
        return curatorFramework;
    }
}
