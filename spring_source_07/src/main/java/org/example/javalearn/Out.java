package org.example.javalearn;

import org.example.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * ClassName:Out
 * Package:org.example.javalearn
 * Description:
 *
 * @Date:2022/1/11 11:01
 * @Author:qs@1.com
 */
@Component
public class Out {

    public Out(){
        System.out.println("this is out");
    }

    public class Inner{
        private OrderService orderService;

        @Autowired
        public Inner(@Lazy OrderService orderService) {
            this.orderService = orderService;
        }


    }

}
