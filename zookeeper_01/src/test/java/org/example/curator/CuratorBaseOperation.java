package org.example.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * ClassName:CuratorBaseOperation
 * Package:org.example.curator
 * Description:
 *
 * @Date:2022/4/12 15:33
 * @Author:qs@1.com
 */
public class CuratorBaseOperation extends CuratorStandaloneBase{

    private static final Logger log = LoggerFactory.getLogger(CuratorBaseOperation.class);

    private static final String CURATOR_NODE = "/curator_node";
    private static final String CURATOR_PARENT_NODE = "/curator_parent_node/sub_node";

    // protection 模式，防止由于异常原因，导致僵尸节点
    // 实际就是加个唯一标识前缀
    @Test
    public void testCreate() throws Exception {
        CuratorFramework curatorFramework = getCuratorFramework();
        String s = curatorFramework.create()
                //.withProtection()
                .withMode(CreateMode.PERSISTENT)
                .forPath(CURATOR_NODE, "curator".getBytes());

        log.info("path {} created!", s);
    }

    @Test
    public void testCreateWithParent() {
        CuratorFramework curatorFramework = getCuratorFramework();
        try {
            String s = curatorFramework.create().creatingParentsIfNeeded().forPath(CURATOR_PARENT_NODE, "parent-sub".getBytes());
            log.info("递归创建：{}", s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetData() {
        CuratorFramework curatorFramework = getCuratorFramework();
        try {
            byte[] data = curatorFramework.getData().forPath(CURATOR_NODE);
            log.info("节点数据：{}", new String(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSetData() {
        CuratorFramework curatorFramework = getCuratorFramework();
        try {
            Stat stat = curatorFramework.setData().forPath(CURATOR_NODE, "xxxxx".getBytes());
            byte[] bytes = curatorFramework.getData().forPath(CURATOR_NODE);
            log.info("节点新的数据：{}", new String(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * guaranteed：该函数的功能如字面意思一样，主要起到一个保障删除成功的作用，其底层工作
     * 方式是：只要该客户端的会话有效，就会在后台持续发起删除请求，直到该数据节点在
     * ZooKeeper 服务端被删除。
     * deletingChildrenIfNeeded：指定了该函数后，系统在删除该数据节点的时候会以递归的方式
     * 直接删除其子节点，以及子节点的子节点。
     */
    @Test
    public void testDelete() {
        CuratorFramework curatorFramework = getCuratorFramework();
        try {
            curatorFramework.delete().guaranteed().deletingChildrenIfNeeded().forPath("/curator_parent_node");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testListChildren() {
        CuratorFramework curatorFramework = getCuratorFramework();
        try {
            List<String> list = curatorFramework.getChildren().forPath("/parent/sub1");
            list.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testThreadPool() throws Exception {
        CuratorFramework curatorFramework = getCuratorFramework();
       curatorFramework.getData().inBackground(new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                log.info("异步执行：{}", event);
            }
        }, Executors.newSingleThreadExecutor()).forPath(CURATOR_NODE);

        log.info("主线程结束。。。。。。");
    }

}
