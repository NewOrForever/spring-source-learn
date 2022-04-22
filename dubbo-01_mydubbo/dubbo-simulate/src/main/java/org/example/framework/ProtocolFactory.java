package org.example.framework;

import org.example.framework.dubbo.DubboProtocol;
import org.example.framework.protocol.HttpProtocol;

/**
 * ClassName:ProtocolFactory
 * Package:org.example.framework
 * Description:
 *
 * @Date:2022/4/22 9:23
 * @Author:qs@1.com
 */
public class ProtocolFactory {

    public static Protocol getProtocol() {
        String protocolName = System.getProperty("protocolName");
        if ("dubbo".equals(protocolName)) {
            return new DubboProtocol();
        }

        return new HttpProtocol();
    }
}
