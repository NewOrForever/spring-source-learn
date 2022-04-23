package com.example.provider.service;

import com.example.DemoService;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;

import java.util.concurrent.TimeUnit;

// 用group来区分一个服务接口的多种实现
//@Service(version = "timeout", group = "timeout", timeout = 5000) // 服务端6s超时
public class TimeoutDemoService implements DemoService {
    @Override
    public String sayHello(String name) {
        System.out.println("开始执行timeout服务" + name);

        // 服务执行5秒
        // 服务超时时间为3秒，但是执行了5秒，服务端会把任务执行完的
        // 服务的超时时间，是指如果服务执行时间超过了指定的超时时间则会抛一个warn
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("结束服务" + name);

        URL url = RpcContext.getContext().getUrl();
        return String.format("%s：%s, hello, %s", url.getProtocol(), url.getPort(), name);
    }
}
