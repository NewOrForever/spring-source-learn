package org.example.curator.watcher;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.example.curator.CuratorStandaloneBase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试curator监听器
 */
public class CuratorListenerTest extends CuratorStandaloneBase {
    private static final Logger log = LoggerFactory.getLogger(CuratorListenerTest.class);

    @Test
    public void testCuratorListener() {
        CuratorFramework curatorFramework = getCuratorFramework();
        curatorFramework.getCuratorListenable().addListener(new CuratorListener() {
            @Override
            public void eventReceived(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                log.info("things changed1：event：{}，{}", curatorEvent.getType().name(), curatorEvent);
            }
        });

        String path = "/curator_listener";
        try {
            // create同步
            createIfNeed(path);

            // 异步
            log.info("开始修改节点{}的数据", path);
            curatorFramework.setData().inBackground().forPath(path, "xxx01".getBytes());
            log.info("第二次修改节点{}的数据", path);
            curatorFramework.setData().inBackground().forPath(path, "xxx02".getBytes());
            curatorFramework.create().inBackground().forPath("/create_listener", "listener".getBytes());

            // watcher -- 一次性监听，如果要持续接听的话需要循环的设置监听
            byte[] bytes = curatorFramework.getData().usingWatcher(new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    log.info("触发getData()的watcher事件：{}", event);
                }
            }).forPath(path);
            log.info("节点{}的最新数据为：{}", path, new String(bytes));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
