package com.example.provider.service;

import com.example.CallbackListener;
import com.example.DemoService;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.annotation.Argument;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName:CallBackDemoService
 * Package:com.example.provider.service
 * Description:
 *
 * @Date:2022/4/24 9:31
 * @Author:qs@1.com
 */

/**
 *  DemoService的sayHello方法的index=2的参数是回调对象，服务消费者可以调用sayHello方法
 *  来添加回调对象，服务提供者一旦执行回调对象的方法就会通知给服务消费者
 *  callbacks：同一个连接回调最大回调实例个数，超过会报错：callback instances num exceed providers limit :3 ,current num: 4.
 *  测试：
 *      - 4个回调实例 ---> 报错
 *          demoService.sayHello("哈哈", "d1", new CallbackListenerImpl());
 *          demoService.sayHello("哈哈", "d2", new CallbackListenerImpl());
 *          demoService.sayHello("哈哈", "d3", new CallbackListenerImpl());
 *          demoService.sayHello("哈哈", "d4", new CallbackListenerImpl());
 *      - 同一个实例使用四次 ---> 不报错
 *         CallbackListenerImpl callbackListener = new CallbackListenerImpl();
 *         demoService.sayHello("哈哈", "d1", callbackListener);
 *         demoService.sayHello("哈哈", "d2", callbackListener);
 *         demoService.sayHello("哈哈", "d3", callbackListener);
 *         demoService.sayHello("哈哈", "d4", callbackListener);
 *
 */
//@Service(version = "callback", methods = {
//      @Method(name = "sayHello", arguments = {@Argument(index = 2, callback = true)})}, callbacks = 3)
public class CallBackDemoService implements DemoService {
    private final Map<String, CallbackListener> listeners = new ConcurrentHashMap<String, CallbackListener>();

    public CallBackDemoService() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for (Map.Entry<String, CallbackListener> entry : listeners.entrySet()) {
                        entry.getValue().changed(getChanged(entry.getKey()));
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        t.start();
    }

    private String getChanged(String key) {
        return "Changed: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    @Override
    public String sayHello(String name) {
        return null;
    }

    @Override
    public String sayHello(String name, String key, CallbackListener callback) {
        System.out.println("执行了回调服务" + name);

        callback.changed("xxxx");


        listeners.put(key, callback);
        URL url = RpcContext.getContext().getUrl();
        return String.format("%s：%s, Hello, %s", url.getProtocol(), url.getPort(), name);  // 正常访问
    }
}
