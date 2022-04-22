package org.example.provider;

import org.example.framework.Protocol;
import org.example.framework.ProtocolFactory;
import org.example.framework.ProxyFactory;
import org.example.framework.URL;
import org.example.framework.protocol.HttpServer;
import org.example.framework.register.LocalRegister;
import org.example.framework.register.RemoteMapRegister;
import org.example.provider.api.HelloService;
import org.example.provider.impl.HelloServiceImpl;

/**
 * ClassName:Provider
 * Package:org.example.provider
 * Description:服务提供者
 *
 * @Date:2022/4/21 14:48
 * @Author:qs@1.com
 */
public class Provider {

    /**
     * 服务启动的时候：
     *  1、注册服务
     *  2、本地注册
     *  3、启动tomcat
     */
    public static void main(String[] args) {
        URL url = new URL();
        url.setHostname("localhost");
        url.setPort(8080);

        // 远程注册服务
        RemoteMapRegister.regist(HelloService.class.getName(), url);

        // 注册本地服务
        LocalRegister.regist(HelloService.class.getName(), HelloServiceImpl.class);

        // 启动tomcat
        Protocol protocol = ProtocolFactory.getProtocol();
        protocol.start(url);
    }

}
