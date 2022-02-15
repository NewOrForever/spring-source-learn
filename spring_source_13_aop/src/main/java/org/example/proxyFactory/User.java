package org.example.proxyFactory;

import org.springframework.stereotype.Component;

/**
 * ClassName:User
 * Package:org.example.proxyFactory
 * Description:
 *
 * @Date:2022/2/11 15:25
 * @Author:qs@1.com
 */
public class User implements UserInterface {
    @Override
    public void execute() {
        System.out.println("org.example.proxyFactory.User.execute");
//        throw new IllegalArgumentException();
//        throw new NullPointerException();
    }

    public void a() {
        System.out.println("a方法");
    }
}
