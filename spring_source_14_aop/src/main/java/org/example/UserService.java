package org.example;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Component;

/**
 * ClassName:UserService
 * Package:org.example
 * Description:
 *
 * @Date:2022/2/13 21:06
 * @Author:qs@1.com
 */
@Component
public class UserService implements UserServiceInterface{
    @Override
    public void execute() {
//        UserService userServiceProxy = (UserService) AopContext.currentProxy();
        System.out.println("org.example.UserService#execute");
    }
}
