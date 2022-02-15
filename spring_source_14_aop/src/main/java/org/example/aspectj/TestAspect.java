package org.example.aspectj;

import org.example.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestAspect {
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
