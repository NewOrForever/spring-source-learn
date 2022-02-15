package org.example.aspectj;

import org.springframework.stereotype.Component;

/**
 * ClassName:MyAspectjUser
 * Package:org.example.aspectj
 * Description:
 *
 * @Date:2022/2/12 21:38
 * @Author:qs@1.com
 */
@Component
public class MyAspectjUser {

    public void executeAcj() {
        System.out.println("执行MyAspectjUser#executeAcj");
    }

    public void a() {
        System.out.println("a方法");
    }


}
