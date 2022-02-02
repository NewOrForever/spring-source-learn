package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * ClassName:AService
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/14 14:58
 * @Author:qs@1.com
 */
@Component
public class AService {

    @Autowired
    @Lazy
    private BService bService;

    // 构造方法注入的时候循环依赖抛错
    // 因为构造方法注入的时候A还没有实例化，缓存就用不了啊，B的A属性注入的时候缓存中没有就又得去创建，重复如此
//    @Lazy
//    public AService(BService bService) {
//        this.bService = bService;
//    }

    @Async
    public void test() {
        System.out.println("aservice");
    }

}
