package org.example.client;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ClassName:ZooReviewTest
 * Package:org.example.client
 * Description: Zookeeper复习，写一下那个循环监听的东西吧
 *
 * @Date:2022/5/26 20:05
 * @Author:qs@1.com
 */
public class ZooReviewTest extends StandaloneBase {
    private static final Logger logger = LoggerFactory.getLogger(ZooReviewTest.class);
    private static final String NODE_TEST_WATCHER_CIRCLE = "/node_test_watcher_circle";

    @Test
    public void testCircleWatcher() {
        ZooKeeper zooKeeper = getZooKeeper();

        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getType() == Event.EventType.NodeDataChanged &&
                        NODE_TEST_WATCHER_CIRCLE.equals(event.getPath())) {
                    try {
                        Stat stat = new Stat();
                        byte[] data = zooKeeper.getData(event.getPath(), this, stat);
                        System.out.println("新数据：========> " + new String(data));
                        System.out.println("新数据stat：========>" + stat);
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        try {
            if (zooKeeper.exists(NODE_TEST_WATCHER_CIRCLE, false) == null) {
                zooKeeper.create(NODE_TEST_WATCHER_CIRCLE, "xxx".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

            //zooKeeper.setData(NODE_TEST_WATCHER_CIRCLE, "what are we still".getBytes(), -1);
            byte[] data = zooKeeper.getData(NODE_TEST_WATCHER_CIRCLE, watcher, null);
            System.out.println("原始数据：-------------> " + new String(data));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
