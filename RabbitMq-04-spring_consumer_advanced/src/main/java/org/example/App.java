package org.example;

import org.example.configuration.RabbitMqConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * ClassName:App
 * Package:org.example
 * Description:
 *
 * @Date:2022/4/18 14:59
 * @Author:qs@1.com
 */
public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RabbitMqConfiguration.class);
    }
}
