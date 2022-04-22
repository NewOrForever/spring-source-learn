package org.example.framework;

/**
 * ClassName:Protocol
 * Package:org.example.framework
 * Description:
 *
 * @Date:2022/4/22 9:19
 * @Author:qs@1.com
 */
public interface Protocol {
    void start(URL url);

    String send(URL url, Invocation invocation);
}
