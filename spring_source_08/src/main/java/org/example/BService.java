package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * ClassName:BService
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/14 14:58
 * @Author:qs@1.com
 */
@Component
public class BService {

    @Autowired
    private AService aService;

//    public BService(AService aService) {
//        this.aService = aService;
//    }

    //    @Async
    public void testB() {
        System.out.println("BService test 。。。");
    }

}
