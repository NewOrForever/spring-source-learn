package org.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * ClassName:MyAspect
 * Package:org.example.aspect
 * Description:
 *
 * @Date:2021/12/20 15:15
 * @Author:qs@1.com
 */
@Aspect
@Component
public class MyAspect {
//    @Before("execution(* org.example..*(..))")
    @Before("execution(* org.example.UserService.test())")
    public void MyAspectBefore(JoinPoint jp) {
         System.out.println("myaspectbefore。。。");
    }
}
