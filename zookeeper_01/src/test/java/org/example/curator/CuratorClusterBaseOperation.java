package org.example.curator;

import org.apache.curator.framework.CuratorFramework;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * ClassName:CuratorClusterBase
 * Package:org.example.curator
 * Description:
 *
 * @Date:2022/4/13 9:53
 * @Author:qs@1.com
 */
public class CuratorClusterBaseOperation extends CuratorClusterBase {
    private static final Logger log = LoggerFactory.getLogger(CuratorClusterBaseOperation.class);

    private static final String TEST_CLUSTER = "/testCluster";

    @Test
    public void testCluster() {
        CuratorFramework curatorFramework = getCuratorFramework();
        try {
            byte[] bytes = curatorFramework.getData().forPath(TEST_CLUSTER);
            log.info("节点{}的数据：{}", TEST_CLUSTER, new String(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                byte[] bytes2 = curatorFramework.getData().forPath(TEST_CLUSTER);
                log.info("======节点{}的数据：{}", TEST_CLUSTER, new String(bytes2));

                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
                testCluster();
            }

        }
    }
}
