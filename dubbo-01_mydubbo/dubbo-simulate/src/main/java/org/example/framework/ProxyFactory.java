package org.example.framework;

import org.example.framework.protocol.HttpProtocol;
import org.example.framework.register.RemoteMapRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Random;

public class ProxyFactory<T> {
    @SuppressWarnings("unchecked")
    public static <T> T getProxy(final Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 将invocation数据发送到provider ---> 执行方法获取返回值
                Invocation invocation = new Invocation(interfaceClass.getName(), method.getName(), method.getParameterTypes(), args);
                // 远程注册中心（应该是要用zk/redis）拿数据
                // 这里用了jvm级别的缓存，开了两个jvm，consumer这个进程的map肯定没数据啊
                // 临时解决：将数据写到这台机器的File文件
                List<URL> urlList = RemoteMapRegister.get(interfaceClass.getName());
                // 负载均衡（这里用随机的策略拿一个url）
                URL url = LoadBalance.random(urlList);
                String res = new HttpProtocol().send(url, invocation);
                System.out.println(res);
                return res;

            }
        });
    }
}
