package org.example.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
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

        // 测试Curator的重连
        // 注意啊linux的端口要开啊
        // netstat -ntp | grep 192.168.65.178查看java客户端连着哪台机器，然后stop该台机器看curator的重连
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

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy=new ExponentialBackoffRetry( 5*1000, 10 );

//        String connectStr = "192.168.0.110:2181,192.168.0.110:2182,192.168.0.110:2183,192.168.0.110:2184";
        String connectStr = "192.168.65.227:2181,192.168.65.227:2182,192.168.65.227:2183,192.168.65.227:2184";
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(connectStr, retryPolicy);
        curatorFramework.start();

        String pathWithParent = "/testCluster";
        byte[] bytes = curatorFramework.getData().forPath(pathWithParent);
        System.out.println(new String(bytes));
        while (true) {

            try {
                byte[] bytes2 = curatorFramework.getData().forPath(pathWithParent);
                System.out.println(new String(bytes2));

                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
