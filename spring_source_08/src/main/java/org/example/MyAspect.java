package org.example;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * ClassName:MyAspect
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/14 15:55
 * @Author:qs@1.com
 */
@Component
@Aspect
public class MyAspect {

    @Pointcut("execution(* org.example..*(..))")
    public void point_cut(){

    }

    @Before("point_cut()")
    public void before(){
        System.out.println("aop before。。。");
    }

}
