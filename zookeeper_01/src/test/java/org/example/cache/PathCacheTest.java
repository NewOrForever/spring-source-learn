package org.example.cache;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.example.curator.CuratorStandaloneBase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PathCacheTest extends CuratorStandaloneBase {
    private static final Logger log = LoggerFactory.getLogger(PathCacheTest.class);

    private static final String PATH_CACHE = "/path-cache";

    @Test
    public void testPathCache() throws Exception {
        CuratorFramework curatorFramework = getCuratorFramework();

        createIfNeed(PATH_CACHE);

        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, PATH_CACHE, true);
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                log.info("event:  {}",event);
            }
        });

        // 如果设置为true则在首次启动时就会缓存节点内容到Cache中
        pathChildrenCache.start(true);
    }

}
