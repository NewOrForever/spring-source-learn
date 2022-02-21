package org.example.bridge;

import org.example.UserService;

/**
 * ClassName:DefaultFoo
 * Package:org.example.bridge
 * Description:
 *
 * @Date:2022/2/17 21:25
 * @Author:qs@1.com
 */
public class DefaultFoo implements  IFoo<UserService>{


    @Override
    public void bar(UserService userService) {
        System.out.println("xxxxx");
    }

    public static void main(String[] args) {
        new DefaultFoo().bar(new UserService());
    }


}
