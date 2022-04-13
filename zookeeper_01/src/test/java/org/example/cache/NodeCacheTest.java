package org.example.cache;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.example.curator.CuratorStandaloneBase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.NOPLogger;

public class NodeCacheTest extends CuratorStandaloneBase {
    private static final Logger log = LoggerFactory.getLogger(NodeCacheTest.class);

    private static final String NODE_CACHE = "/node_cache";

    @Test
    public void testNodeCache() throws Exception {
        CuratorFramework curatorFramework = getCuratorFramework();

        createIfNeed(NODE_CACHE);

        NodeCache nodeCache = new NodeCache(curatorFramework, NODE_CACHE);
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                log.info("{} path nodechanged!", NODE_CACHE);
            }
        });

        nodeCache.start();
    }

    public void printNodeData() throws Exception {
        byte[] bytes = getCuratorFramework().getData().forPath(NODE_CACHE);
        log.info("data：{}", new String(bytes));
    }
}
