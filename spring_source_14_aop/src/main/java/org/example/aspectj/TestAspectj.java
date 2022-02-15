package org.example.aspectj;

import org.example.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * ClassName:TestAspectj
 * Package:org.example.aspectj
 * Description:
 *
 * @Date:2022/2/12 21:37
 * @Author:qs@1.com
 */
public class TestAspectj {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        MyAspectjUser myAspectjUser = (MyAspectjUser) context.getBean("myAspectjUser");
        myAspectjUser.executeAcj();
        myAspectjUser.a();

        // introduce
        IntroduceUserInterface introduceUserInterface = (IntroduceUserInterface) myAspectjUser;
        introduceUserInterface.intoduceUserExecute();

    }
}
