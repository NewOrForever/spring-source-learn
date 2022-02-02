package org.example;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * ClassName:MyApplicationListener
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/24 16:39
 * @Author:qs@1.com
 */
@Component
public class MyApplicationListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof PayloadApplicationEvent) {
            System.out.println(((PayloadApplicationEvent) event).getPayload());
        }
        else {
            System.out.println(event);
        }
    }
}
