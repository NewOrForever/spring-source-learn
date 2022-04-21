package org.example.listener;

import org.example.configuration.RabbitMqConfiguration;
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

@Component
public class RabbitListenerAnnotationHandler {

    /************测试helloworld**************/
    @RabbitListener(queues = {RabbitMqConfiguration.SPRING_QUEUE})
    public void handleSpringQueue02(Message message) {
        System.out.println("02接收到的消息：===========> " + new String(message.getBody()));
    }

    /************测试workqueue**************/
    @RabbitListener(queues = {RabbitMqConfiguration.SPRING_WORK_QUEUE})
    public void testWorkQueue01(Message message) {
        System.out.println("WorkQueue01111接收到的消息：===========> " + new String(message.getBody()));
    }
    @RabbitListener(queues = {RabbitMqConfiguration.SPRING_WORK_QUEUE})
    public void testWorkQueue02(Message message) {
        System.out.println("WorkQueue02222接收到的消息：===========> " + new String(message.getBody()));
    }

    /************测试pubsub（交换机fanout） - 这里开始要去绑定交换机和队列了**************/
    @RabbitListener(queues = {RabbitMqConfiguration.SPRING_PUBSUB_QUEUE_01})
    public void testPubSubQueue01(Message message) {
        System.out.println("testPubSubQueue01111接收到的消息：===========> " + new String(message.getBody()));
    }
    @RabbitListener(queues = {RabbitMqConfiguration.SPRING_PUBSUB_QUEUE_02})
    public void testPubSubQueue02(Message message) {
        System.out.println("testPubSubQueue02222接收到的消息：===========> " + new String(message.getBody()));
    }

    /************测试routing（交换机direct） - 在pubsub基础上增加精确匹配的routingKey**************/
    @RabbitListener(queues = {RabbitMqConfiguration.SPRING_ROUTING_QUEUE_01})
    public void testRouting01(Message message) {
        System.out.println("testRouting011111接收到的消息：===========> " + new String(message.getBody()));
    }
    @RabbitListener(queues = {RabbitMqConfiguration.SPRING_ROUTING_QUEUE_02})
    public void testRouting02(Message message) {
        System.out.println("testRouting022222接收到的消息：===========> " + new String(message.getBody()));
    }

    /************测试topic（交换机topic） - 使用模糊匹配的routingKey*************/
    @RabbitListener(queues = {RabbitMqConfiguration.SPRING_TOPIC_QUEUE_01})
    public void testTopic01(Message message) {
        System.out.println("testTopic011111接收到的消息：===========> " + new String(message.getBody()));
    }
    @RabbitListener(queues = {RabbitMqConfiguration.SPRING_TOPIC_QUEUE_02})
    public void testTopic02(Message message) {
        System.out.println("testTopic022222接收到的消息：===========> " + new String(message.getBody()));
    }


}
