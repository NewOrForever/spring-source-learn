package org.example;

import org.example.configuration.RabbitMqConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试RabbitMq的高级特性
 * - 消息的可靠性投递
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RabbitMqConfiguration.class})
public class ProducerTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /*************************消息可靠性投递的测试***************************/
    @Test
    public void testConfirm() {
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             * @param correlationData 相关配置信息
             * @param ack   exchange交换机 是否成功收到了消息。true 成功，false代表失败
             * @param cause 失败原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                // ack 为  true表示 消息已经到达交换机
                if (ack) {
                    System.out.println("消息接收成功：" + correlationData + "=======> cause：" + cause);
                } else {
                    // 接收失败
                    System.out.println("消息接收失败："+ correlationData + "=======> cause：" + cause);
                    /**
                     * 做一些处理让消息再次发送
                      */

                }
            }
        });

        // 消息发送
        rabbitTemplate.convertAndSend("test_confirm_exchange", "confirm", "message confirm....");

        // 测试的时候为了防止回调没进来线程就终止了，加个sleep
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReturn() {
        // rabbitTemlate需要去设置mandatory属性为true，这里已经在@Bean中设置了
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            /**
             * message   消息对象
             * replyCode 错误码
             * replyText 错误信息
             * exchange  交换机
             * routingKey 路由键
             */
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                System.out.println("回退的消息：======> " + returned);
            }
        });

        // 消息发送 - 测试：把路由写错了 -> 回调callback
        rabbitTemplate.convertAndSend("test_confirm_exchange", "confirm01", "message confirm....");

        // 测试的时候为了防止回调没进来线程就终止了，加个sleep
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*****************测试消费端限流*****************/
    // 批量发送消息，让消费者每次拉去指定的数量
    @Test
    public void testQos() {
        for (int i = 0; i < 10; i++) {
            // 发送消息
            rabbitTemplate.convertAndSend("test_confirm_exchange", "confirm", i + "message confirm....");
        }
    }

    /*****************测试ttl*****************/
    // 批量发送消息，让消费者每次拉去指定的数量
    @Test
    public void testTtl() {
        for (int i = 0; i < 10; i++) {
            // 发送消息
            rabbitTemplate.convertAndSend(RabbitMqConfiguration.TEST_TTL_EXCHANGE, "ttl." + i, i + "message confirm....");
        }
    }

    /*****************测试死信队列*****************/
    // 批量发送消息，让消费者每次拉去指定的数量
    @Test
    public void testDlx() {
        for (int i = 0; i < 12; i++) {
            // 发送消息
            rabbitTemplate.convertAndSend(RabbitMqConfiguration.TEST_DLX_EXCHANGE, "dlx.routing." + i, i + "message dlx....");
        }

    }

}
