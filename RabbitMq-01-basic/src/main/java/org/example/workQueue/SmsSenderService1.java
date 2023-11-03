package org.example.workQueue;

import com.rabbitmq.client.*;
import org.example.BaseMQ;
import org.example.MyConstant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SmsSenderService1 extends BaseMQ {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(MyConstant.WORK_QUEUE, false, false, false, null);
        // 如果不写basicQos(1)，则自动MQ会将所有请求平均发送给所有消费者
        // basicQos,MQ不再对消费者一次发送多个请求，而是消费者处理完一个消息后（确认后），在从队列中获取一个新的
        // 这样就能让性能好的机器消费更多的消息
        channel.basicQos(1);// 处理完一个取一个

        channel.basicConsume(MyConstant.WORK_QUEUE, false, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("SMSSender1-短信发送成功: ================> " + new String(body));

                // 使用不同的sleep来模拟机器的好坏
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // false只确认签收当前的消息，设置为true时则代表签收该消费者所有未签收的消息
                getChannel().basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }

}
