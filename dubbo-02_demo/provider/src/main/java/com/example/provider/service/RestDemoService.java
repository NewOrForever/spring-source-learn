package com.example.provider.service;

import com.example.DemoService;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.protocol.rest.support.ContentType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * ClassName:RestDemoService
 * Package:com.example.provider.service
 * Description:
 *
 * @Date:2022/4/25 9:07
 * @Author:qs@1.com
 */
//@Service(version = "rest", protocol = "p2")
@Path("rest")
public class RestDemoService implements DemoService {

    @Path("say/{name}")
    @GET
    @Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
    @Override
    public String sayHello(@PathParam(value = "name") String name) {
        return "hello！" + name + ", this is rest protocol";
    }
}
