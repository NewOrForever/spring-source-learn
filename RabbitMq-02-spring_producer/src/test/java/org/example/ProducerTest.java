package org.example;

import org.example.configuration.RabbitMqConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试使用javaconfig方式来spring整合rabbitmq（xml方式有，就不测试了）
 *  - pom.xml中添加spring-test依赖 ---> 使用@ContextConfiguration注解来引入Configuration类
 *  - 需要@RunWith(SpringJUnit4ClassRunner.class)这个，且junit的版本要不小于4.12
 *  - 当然也可以使用手动写AnnotationConfigApplicationContext的方式，手动getBean
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( classes = {RabbitMqConfiguration.class})
public class ProducerTest
{
    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 测试helloworld
    @Test
    public void testHelloWorld() {
        rabbitTemplate.convertAndSend(RabbitMqConfiguration.SPRING_QUEUE, "javaconfig_hello_world");
    }

    // 测试workqueue

    // 测试pubsub

    // 测试routing

    // 测试topic

}
