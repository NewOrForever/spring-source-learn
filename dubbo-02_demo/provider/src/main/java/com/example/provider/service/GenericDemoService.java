package com.example.provider.service;

import com.example.DemoService;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericException;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.concurrent.CompletableFuture;

/**
 * ClassName:GenericDemoService
 * Package:com.example.provider.service
 * Description:
 *
 * @Date:2022/4/22 15:45
 * @Author:qs@1.com
 */

/**
 * 测试泛接口实现方式
 * - 泛接口实现方式主要用于服务器端没有 API 接口及模型类元的情况
 * - ConsumerApplication中正常Reference
 * - 所有的方法都会走这里
 */
//@Service(interfaceName = "com.example.DemoService", version = "generic")
public class GenericDemoService implements GenericService {

    @Override
    public Object $invoke(String method, String[] parameterTypes, Object[] args) throws GenericException {
        System.out.println("执行了generic服务");

        return "执行的方法是" + method;
    }

}
