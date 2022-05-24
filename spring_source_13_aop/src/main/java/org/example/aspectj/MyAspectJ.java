package org.example.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * ClassName:MyAspectJ
 * Package:org.example.aspectj
 * Description: 执行顺序的要注意一下around是最先执行的，after (实际是after finally)在after returnning之后执行
 *
 * @Date:2022/2/12 21:21
 * @Author:qs@1.com
 */

// 这个切面类也得是个bean哦，AppConfig的enable注解不要忘记写
@Aspect
@Component
public class MyAspectJ {

    // 为已有的类引入一个新的接口，也可以理解为把当前对象转型为另一个对象
    // value -  target type
    // defaultImpl - 新接口的实现类（最后执行方法的类）
    @DeclareParents(value = "org.example.aspectj.MyAspectjUser", defaultImpl = IntroduceUserImpl.class)
    private IntroduceUserInterface introduceUserInterface;


    @Pointcut("execution(* org.example.aspectj.*.execute*(..))")
    public void pointcut() {
    }

//    @Pointcut(value = "execution(* org.example.aspectj.*.test(..)) && args(a, b)", argNames = "a, b")

    @Before("pointcut()")
    public void before() {
        System.out.println("执行aspectj before。。。");
    }

    @Around("pointcut()")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("执行aspectj around before。。。");
        // ProceedingJoinPoint extends JointPoint  ---  这个proceed  ---> ReflectiveMethodInvocation中最后执行的就是method.invoke(target, args)
        // 实际就是执行了目标对象的方法
        pjp.proceed();
        System.out.println("执行aspectj around after。。。");
    }

    @After("pointcut()")
    public void after() {
        System.out.println("执行aspectj after finally 。。。");
    }

    @AfterReturning("pointcut()")
    public void afterReturning() {
        System.out.println("执行aspectj afterreturnnig 。。。");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        System.out.println("执行aspectj afterThrowing 。。。");
    }
}
