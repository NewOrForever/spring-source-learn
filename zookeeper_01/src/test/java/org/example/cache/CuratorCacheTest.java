package org.example.cache;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.example.curator.CuratorStandaloneBase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName:CuratorCacheTest
 * Package:org.example.cache
 * Description:
 *
 * @Date:2022/4/13 8:44
 * @Author:qs@1.com
 */
public class CuratorCacheTest extends CuratorStandaloneBase {
    private static final Logger log = LoggerFactory.getLogger(CuratorCacheTest.class);

    private static final String CURATOR_PATH = "/curator_path";

    /**
     * 后续再测试吧，下面这样为啥会报错
     */
    @Test
    public void testCuratorCache() {

//        CuratorCache cache = CuratorCache.build(getCuratorFramework(), CURATOR_PATH);
//        CuratorCacheListener listener = CuratorCacheListener.builder()
//                .forCreates(node -> System.out.println(String.format("Node created: [%s]", node)))
//                .forChanges((oldNode, node) -> System.out.println(String.format("Node changed. Old: [%s] New: [%s]", oldNode, node)))
//                .forDeletes(oldNode -> System.out.println(String.format("Node deleted. Old value: [%s]", oldNode)))
//                .forInitialized(() -> System.out.println("Cache initialized"))
//                .build();
//
//        // register the listener
//        cache.listenable().addListener(listener);
//
//        // the cache must be started
//        cache.start();

    }
}
