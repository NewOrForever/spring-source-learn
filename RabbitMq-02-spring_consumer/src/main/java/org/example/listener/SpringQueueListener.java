package org.example.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * ClassName:SpringQueueListener
 * Package:org.example
 * Description:
 *
 * @Date:2022/4/18 15:00
 * @Author:qs@1.com
 */

public class SpringQueueListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("接收到的消息：===========> " + message);
    }
}
