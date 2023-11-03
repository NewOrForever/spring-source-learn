package com.example.provider.service;

import com.example.DemoService;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.protocol.rest.support.ContentType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:TagDemoService
 * Package:com.example.provider.service
 * Description: 测试标签路由规则
 *
 * @Date:2022/4/22 15:45
 * @Author:qs@1.com
 */

//@Service(version = "tag")
public class TagDemoService implements DemoService {
    @Override
    public String sayHello(String name) {
        System.out.println("执行了服务" + name);

        URL url = RpcContext.getContext().getUrl();

        return String.format("%s：%s, Hello, %s", url.getProtocol(), url.getPort(), name);  // 正常访问
    }
}
