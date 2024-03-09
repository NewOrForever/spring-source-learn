package org.example;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
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

    private static final String CONNECT_STR = "192.168.50.65:2181";
    private static final Integer SESSION_TIMEOUT = 30 * 1000;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

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

        MyConfig myConfig = new MyConfig();
        myConfig.setKey("mykey");
        myConfig.setName("myname");
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsBytes(myConfig);

        String res = zooKeeper.create("/myconfig", bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                logger.info("{} 发生了数据变化", event.getPath());
                // WatchedEvent state:SyncConnected type:NodeDataChanged path:/myconfig
                if (event.getType() == Event.EventType.NodeDataChanged &&
                event.getPath() != null && event.getPath().equals("/myconfig")) {
                    try {
                        byte[] data = zooKeeper.getData("/myconfig", this, null);
                        logger.info("新的数据为：{}", new String(data));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        byte[] data = zooKeeper.getData("/myconfig", watcher, null);
        MyConfig originMyConfig = objectMapper.readValue(data, MyConfig.class);
        logger.info("原始数据为：{}", originMyConfig);
//        JsonNode jsonNode = objectMapper.readTree(data);jsonNode.get("list").get(0).get("fieldName")

        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }
}
