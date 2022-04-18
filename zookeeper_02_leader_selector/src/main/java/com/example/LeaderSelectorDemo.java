package com.example;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:LeaderSelectorDemo
 * Package:com.example
 * Description: leader选举在分布式场景中的应用 - 缓存预热：user服务在集群环境，选举出一个节点进行缓存操作
 *
 * 怎么测试呢？
 *  - 开三个应用啊，虚拟机参数 -DappName=app1，-DappName=app2，-DappName=app3
 *  - 选举出来后关掉这个应用 -----> 会切换到别的应用
 *  - 增加机器也可以测试看看
 *
 * @Date:2022/4/16 10:21
 * @Author:qs@1.com
 */
public class LeaderSelectorDemo {

    private static final String CONNECT_STR = "192.168.65.227:2181";
    private static RetryPolicy retryPolicy = new ExponentialBackoffRetry(5 * 1000, 10);
    private static CuratorFramework curatorFramework = null;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        curatorFramework = CuratorFrameworkFactory.newClient(CONNECT_STR, retryPolicy);
        curatorFramework.start();

        LeaderSelectorListener listener = new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                // 拿到leader后进行的业务操作
                String appName = System.getProperty("appName");
                System.out.println("i am the leader, i am " + appName);

                TimeUnit.SECONDS.sleep(5);
            }
        };

        LeaderSelector leaderSelector = new LeaderSelector(curatorFramework, "/preHotCache_leader", listener);
        // 设置了这个的话选举出的leader执行完业务方法释放后又会去重复执行选举
        // 这个要不要设置看业务场景吧
        leaderSelector.autoRequeue();
        // 看源码可以知道 - 这个LeaderSelector的核心就是公平锁
        leaderSelector.start();

        countDownLatch.await();
    }

}
