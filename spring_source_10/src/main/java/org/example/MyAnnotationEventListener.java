package org.example;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * ClassName:MyAnnotationEventListener
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/24 17:05
 * @Author:qs@1.com
 */
@Component
public class MyAnnotationEventListener {

    @EventListener
    public void testListener(ApplicationEvent event) {
        System.out.println("EventListener========" + event);
    }
}
