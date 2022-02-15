package org.example.cglibProxy;

/**
 * ClassName:TestCgProxy
 * Package:org.example.cglibProxy
 * Description:
 *
 * @Date:2022/2/11 14:23
 * @Author:qs@1.com
 */
public class TestCgProxy {
    public static void main(String[] args) {
        CglibProxyFactory proxyFactory = new CglibProxyFactory();
        proxyFactory.setTarget(new CgUser());
        CgUser proxyInstance = (CgUser) proxyFactory.getProxyInstance();
        proxyInstance.execute();
        proxyInstance.a();
    }
}
