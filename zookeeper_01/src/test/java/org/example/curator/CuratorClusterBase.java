package org.example.curator;

/**
 * ClassName:CuratorClusterBase
 * Package:org.example.curator
 * Description:
 *
 * @Date:2022/4/13 9:53
 * @Author:qs@1.com
 */
public class CuratorClusterBase extends CuratorStandaloneBase{
    private static final String CLUSTER_CONNECT_STR = "192.168.65.227:2181,192.168.65.227:2182,192.168.65.227:2183,192.168.65.227:2184";

    protected String getConnectStr() {
        return CLUSTER_CONNECT_STR;
    }
}
