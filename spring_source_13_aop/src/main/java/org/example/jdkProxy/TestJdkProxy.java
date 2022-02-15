package org.example.jdkProxy;

/**
 * ClassName:TestJdkProxy
 * Package:org.example.jdkProxy
 * Description: 测试jdk动态代理
 *
 * @Date:2022/2/11 13:48
 * @Author:qs@1.com
 */
public class TestJdkProxy {
    public static void main(String[] args) throws Exception {
        JdkProxyFactory<IJdkUserInterface> proxyFactory = new JdkProxyFactory<>();
        proxyFactory.setTarget(new JdkUser());
        IJdkUserInterface proxyInstance = (IJdkUserInterface) proxyFactory.getProxyInstance();
        proxyInstance.execute();
    }
}
