package com.example.provider.service;

import com.example.DemoService;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.protocol.rest.support.ContentType;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * ClassName:DefaultDemoService
 * Package:com.example.provider.service
 * Description:
 *
 * @Date:2022/4/22 15:45
 * @Author:qs@1.com
 */
// 用group来区分一个服务接口的多种实现
/**
 * 多协议
 *  - rest需要加@Path注解 ---> 其实就是类似于@RequestMapping
  */
//@Service(version = "default", group = "default", protocol = {"p1", "p2"})
//@Service(version = "default", group = "default")
@Path("defaultDemo")
public class DefaultDemoService implements DemoService {
    @GET
    @Path("sayHello")
    @Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
    @Override
    public String sayHello(@QueryParam("name") String name) {
        System.out.println("执行了服务" + name);

        URL url = RpcContext.getContext().getUrl();

        return String.format("%s：%s, Hello, %s", url.getProtocol(), url.getPort(), name);  // 正常访问
    }
}
