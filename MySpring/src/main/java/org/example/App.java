package org.example;

import org.example.my.AppConfig;
import org.example.my.UserService;
import org.example.my.UserServiceInterface;
import org.example.spring.MyAnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        MyAnnotationConfigApplicationContext context = new MyAnnotationConfigApplicationContext(AppConfig.class);

//        System.out.println(context.getBean("userService"));
//        System.out.println(context.getBean("userService"));
//        System.out.println(context.getBean("orderService"));
//        System.out.println(context.getBean("orderService"));

        UserServiceInterface userService = (UserServiceInterface)context.getBean("userService");

        userService.test();


    }


}
