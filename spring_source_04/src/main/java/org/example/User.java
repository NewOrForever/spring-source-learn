package org.example;

import org.springframework.stereotype.Component;

/**
 * ClassName:User
 * Package:org.example
 * Description:
 *
 * @Date:2021/12/29 15:32
 * @Author:qs@1.com
 */
public class User {
    public User() {
        System.out.println("user init construct");
    }

    public void test() {
        System.out.println("user test");
    }
}
