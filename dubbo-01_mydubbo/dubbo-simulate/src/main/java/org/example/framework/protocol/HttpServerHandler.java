package org.example.framework.protocol;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.example.framework.Invocation;
import org.example.framework.register.LocalRegister;
import sun.nio.ch.IOUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ClassName:HttpServerHandler
 * Package:org.example.framework
 * Description:
 *
 * @Date:2022/4/21 15:30
 * @Author:qs@1.com
 */
public class HttpServerHandler {
    public void handler(HttpServletRequest req, HttpServletResponse resp) {

        try {
            // 接收到消费端传递过来的数据
            // Invocation invocation = JSON.parseObject(req.getInputStream(), Invocation.class);

            // jdk11之前用
            // HttpClient发送数据的时候使用ObjectOutputStream，所以这里也需要用ObjectInputStream来读取
            ObjectInputStream objectInputStream = new ObjectInputStream(req.getInputStream());
            Invocation invocation = (Invocation) objectInputStream.readObject();

            // 从本地注册中心拿接口的实现类
            Class implClass = LocalRegister.get(invocation.getInterfaceName());
            Method method = implClass.getMethod(invocation.getMethodName(), invocation.getParamTypes());
            String result = (String) method.invoke(implClass.newInstance(), invocation.getParams());

            System.out.println("tomcat：" + result);
            IOUtils.write(result, resp.getOutputStream());

        } catch (IOException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
