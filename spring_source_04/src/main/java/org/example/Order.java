package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

/**
 * ClassName:OrderService
 * Package:org.example
 * Description:
 *
 * @Date:2021/12/28 15:27
 * @Author:qs@1.com
 */
public class Order {
    public Order() {
        System.out.println("order init construct");
    }

    public void test() {
        System.out.println("order test");
    }

}
