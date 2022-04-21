package com.example.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * ClassName:RabbitListener
 * Package:com.example.listener
 * Description:
 *
 * @Date:2022/4/19 17:07
 * @Author:qs@1.com
 */
@Component
public class MyRabbitListener {

    @RabbitListener(queues = {"boot_topic_queue"})
    public void handleTopicMessage(Message message) {
        System.out.println("topic模式消息处理：=============>" + new String(message.getBody()));
    }

}
