package org.example.client;

/**
 * ClassName:ClusterBase
 * Package:org.example.client
 * Description:
 *
 * @Date:2022/4/13 11:00
 * @Author:qs@1.com
 */
public class ClusterBase extends StandaloneBase {
    private static final String CLUSTER_CONNECT_STR = "192.168.65.227:2181,192.168.65.227:2182,192.168.65.227:2183,192.168.65.227:2184";
    private static final Integer CLUSTER_SESSION_TIMEOUT = 10 * 1000;

    public Integer getSessionTimeout() {
        return CLUSTER_SESSION_TIMEOUT;
    }

    protected String getConnectStr() {
        return CLUSTER_CONNECT_STR;
    }
}
