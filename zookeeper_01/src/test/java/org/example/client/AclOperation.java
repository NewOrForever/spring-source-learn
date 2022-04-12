package org.example.client;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ClassName:AclOperation
 * Package:org.example
 * Description: 权限控制的一些操作
 *
 * @Date:2022/4/12 11:05
 * @Author:qs@1.com
 */
public class AclOperation extends StandaloneBase{

    private static final Logger log = LoggerFactory.getLogger(AclOperation.class);
    private static final String ACL_NODE = "/acl_node";
    private static final String ACL_NODE_AUTH = "/acl_node_auth";

    /**
     * 用world模式创建节点
     */
    @Test
    public void createAclTest01() throws InterruptedException, KeeperException {
        ZooKeeper zooKeeper = getZooKeeper();

        Id id = new Id("world", "anyone");
        int perms =  ZooDefs.Perms.ADMIN | ZooDefs.Perms.READ;
        List<ACL> aclList = new ArrayList<ACL>(
                Collections.singletonList(new ACL(perms, id)));

        String res = zooKeeper.create(ACL_NODE, "acl".getBytes(), aclList, CreateMode.PERSISTENT);
        log.info("create: {}", res);
    }

    /**
     * 用授权模式创建节点
     */
    @Test
    public void createAclTest02() throws InterruptedException, KeeperException {
        ZooKeeper zooKeeper = getZooKeeper();
        zooKeeper.addAuthInfo("digest", "u100:p100".getBytes());

        Id id = new Id("auth", "u100:p100");
        int perms =  ZooDefs.Perms.ADMIN | ZooDefs.Perms.READ;
        List<ACL> aclList = new ArrayList<ACL>(
                Collections.singletonList(new ACL(perms, id)));

        String res = zooKeeper.create(ACL_NODE_AUTH, "acl".getBytes(), aclList, CreateMode.PERSISTENT);
        log.info("create: {}", res);

    }

    /**
     * 测试拿有权限控制的node
     */
    @Test
    public void getByAclTest() {
        ZooKeeper zooKeeper = getZooKeeper();
        // /acl_node_auth该节点的auth信息是 - u100:p100
        // 测试auth信息不对，抛错
        zooKeeper.addAuthInfo("digest", "u200:p200".getBytes());

        try {
            byte[] data = zooKeeper.getData(ACL_NODE_AUTH, false, null);
            log.info("节点数据：{}", new String(data));
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            String encryptAuth = DigestAuthenticationProvider.generateDigest("u100:p100");
            log.info("权限加密：{}", encryptAuth);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
