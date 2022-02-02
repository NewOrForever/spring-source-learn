package org.example;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * ClassName:OrderService
 * Package:org.example
 * Description:
 *
 * @Date:2021/12/31 15:30
 * @Author:qs@1.com
 */
@Component
//@DependsOn("userService")
public class OrderService {

    public OrderService() {
        System.out.println("this is orderService");
    }
}
