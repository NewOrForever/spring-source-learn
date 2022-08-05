package org.example.framework.dubbo;

import org.example.framework.Invocation;
import org.example.framework.Protocol;
import org.example.framework.URL;

/**
 * ClassName:DubboProtocol
 * Package:org.example.framework.dubbo
 * Description:
 *
 * @Date:2022/4/22 9:11
 * @Author:qs@1.com
 */
public class DubboProtocol implements Protocol {
    @Override
    public void start(URL url) {
        new NettyServer().start(url.getHostname(), url.getPort());
    }

    @Override
    public String send(URL url, Invocation invocation) {
        return new NettyClient<>().send(url.getHostname(), url.getPort(), invocation);
    }
}
