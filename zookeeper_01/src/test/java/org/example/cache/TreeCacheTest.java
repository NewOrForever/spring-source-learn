package org.example.cache;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.example.curator.CuratorStandaloneBase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TreeCacheTest extends CuratorStandaloneBase {
    private static final Logger log = LoggerFactory.getLogger(TreeCacheTest.class);

    private static final String TREE_CACHE = "/tree-cache";

    @Test
    public void testTreeCache() throws Exception {
        CuratorFramework curatorFramework = getCuratorFramework();

        createIfNeed(TREE_CACHE);

        TreeCache treeCache = new TreeCache(curatorFramework, TREE_CACHE);
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                log.info("event:  {}",event);

                Map<String, ChildData> currentChildren = treeCache.getCurrentChildren(TREE_CACHE);
                log.info("currentChildren：{}", currentChildren);

            }
        });

        treeCache.start();
    }

}
