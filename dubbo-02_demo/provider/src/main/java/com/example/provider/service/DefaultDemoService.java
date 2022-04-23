package com.example.provider.service;

import com.example.DemoService;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;

/**
 * ClassName:DefaultDemoService
 * Package:com.example.provider.service
 * Description:
 *
 * @Date:2022/4/22 15:45
 * @Author:qs@1.com
 */
// 用group来区分一个服务接口的多种实现
//@Service(version = "default", group = "default")
public class DefaultDemoService implements DemoService {
    @Override
    public String sayHello(String name) {
        System.out.println("执行了服务" + name);

        URL url = RpcContext.getContext().getUrl();

        return String.format("%s：%s, Hello, %s", url.getProtocol(), url.getPort(), name);  // 正常访问
    }
}
