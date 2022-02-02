package org.example.destroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

/**
 * ClassName:User
 * Package:org.example.destroy
 * Description:
 *
 * @Date:2022/1/7 13:02
 * @Author:qs@1.com
 */
@Component
public class User implements DisposableBean, AutoCloseable {
    @Override
    public void destroy() throws Exception {
        System.out.println("user DisposableBean destroy 。。。");
    }

    // 不会执行的
    @Override
    public void close() throws Exception {
        System.out.println("user autoclosable close ...");
    }
}
