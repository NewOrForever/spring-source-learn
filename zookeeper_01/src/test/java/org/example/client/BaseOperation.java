package org.example.client;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName:BaseOperation
 * Package:org.example
 * Description:
 *
 * @Date:2022/4/12 8:35
 * @Author:qs@1.com
 */
public class BaseOperation extends StandaloneBase{
    private static final Logger log = LoggerFactory.getLogger(BaseOperation.class);
    private static final String TEST_NODE = "/test_node";

    @Test
    public void testCreate() throws InterruptedException, KeeperException {
        ZooKeeper zooKeeper = getZooKeeper();
        String res = zooKeeper.create(TEST_NODE, "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        log.info("create：{}", res);
    }

    @Test
    public void testGetData() {
        ZooKeeper zooKeeper = getZooKeeper();

        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getType() == Event.EventType.NodeDataChanged &&
                    event.getPath() != null && event.getPath().equals(TEST_NODE)) {
                    log.info("{}：数据发生改变", event.getPath());
                    try {
                        byte[] newData = zooKeeper.getData(TEST_NODE, this, null);
                        log.info("新的数据为：{}", new String(newData));
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        try {
            byte[] originData = zooKeeper.getData(TEST_NODE, watcher, null);
            log.info("原始数据：{}", new String(originData));
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testSetData()  {
        ZooKeeper zooKeeper = getZooKeeper();

        try {
            Stat stat = new Stat();
            byte[] data = zooKeeper.getData(TEST_NODE, false, stat);
            log.info("原始数据版本：{}", stat.getVersion());

            int version = stat.getVersion();

            // version：-1会匹配所有的版本
            stat = zooKeeper.setData(TEST_NODE, "xxxxx".getBytes(), -1);
            log.info("新的数据版本：{}", stat.getVersion());
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelete() {
        ZooKeeper zooKeeper = getZooKeeper();
        try {
            // -1 代表匹配所有版本，直接删除
            // 任意大于 -1 的代表可以指定数据版本删除
            zooKeeper.delete(TEST_NODE, -1);
            log.info("{}节点已被删除", TEST_NODE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步测试
     * # getData
     */
    @Test
    public void testAsync() {
        ZooKeeper zooKeeper = getZooKeeper();
        // 异步执行，走回调方法
        zooKeeper.getData(TEST_NODE, false, (rc, path, ctx, data,stat) -> {
            Thread thread = Thread.currentThread();
            log.info(" Thread Name: {},   rc:{}, path:{}, ctx:{}, data:{}, stat:{}",thread.getName(),rc, path, ctx, data, stat);
        }, "test");

        Thread mainThread = Thread.currentThread();
        log.info("主线程{}结束。。。。。。。。。", mainThread.getName());

    }
}
