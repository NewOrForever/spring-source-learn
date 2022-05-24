package com.example;

/**
 * ClassName:DemoServiceMock
 * Package:com.example
 * Description:
 *
 * @Date:2022/4/24 13:53
 * @Author:qs@1.com
 */
public class DemoServiceMock implements DemoService{
    @Override
    public String sayHello(String name) {
        // 你可以伪造容错数据，此方法只在出现RpcException时被执行
        return "容错数据";
    }
}
