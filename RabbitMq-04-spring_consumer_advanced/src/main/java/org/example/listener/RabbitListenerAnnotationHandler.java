package org.example.listener;

import com.rabbitmq.client.Channel;
import org.example.configuration.RabbitMqConfiguration;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

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

    /*************************消息可靠性投递的测试 - consumer ack***************************/
    // 测试下面的功能的时候注释掉这个RabbitListener
    //@RabbitListener(queues = {RabbitMqConfiguration.TEST_CONFIRM_QUEUE})
    public void handleTestConfirm(Message message, Channel channel) throws IOException {
        // 1、获取消息的id
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            // 2、获取消息
            System.out.println("接收到的消息：======>" + new String(message.getBody()));

            // 3、进行业务处理
            System.out.println("=======业务处理========");

            // 模拟出现异常 -----> 去到异常处理来进行拒绝签收
             int i = 5 / 0;

            // 4、手动进行消息签收
            channel.basicAck(deliveryTag, false);

        } catch (Exception e) {
            // 拒绝签收
             /*
            第三个参数：requeue：重回队列。如果设置为true，则消息重新回到queue，broker会重新发送该消息给消费端
            如果设置为false，会被丢弃或者转发到死信队列
             */
            channel.basicNack(deliveryTag, false, true);
        }
    }

    /*****************测试消费端限流*****************/
    //@RabbitListener(queues = RabbitMqConfiguration.TEST_CONFIRM_QUEUE)
    public void handleTestQos(Message message, Channel channel) throws InterruptedException, IOException {
        // 在配置类中对RabbitListenerContainerFactory配置prefetchCount为2 ---> 消费者一次性拉取2条消息，处理完后再去拉取2条
        // 测试：先将手动签收注释掉 ---> 可以发现消费者控制台打印了两条消息就不打印了（因为没签收么）
        // 再取消注释去手动签收，可以发现消费者控制台能打印所有的消息

        try {
            // 获取到的消息
            System.out.println("接收到消息：======> " + new String(message.getBody()));

            Thread.sleep(1000);

            // 处理业务逻辑

            // 模拟异常 -> 第一批拉取的2条数据会不断的重复发送进来处理
            //int i = 1 / 0;

            // 进行消息的签收
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
        } catch (Exception e) {
            System.out.println("回退消息：======> " + new String(message.getBody()));
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

    /*****************测试死信队列dlx*******************/
    // 监听普通队列
    // 这里测试拒绝签收（抛个异常）且设置不重回queue而是丢弃消息 ---> 进入死信交换机  ---> 进入死信队列
    //@RabbitListener(queues = {RabbitMqConfiguration.TEST_DLX_QUEUE})
    public void handleTestDxl(Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            //1.接收转换消息
            System.out.println(new String(message.getBody()));

            //2. 处理业务逻辑
            System.out.println("处理业务逻辑...");
            //int i = 3/0;//出现错误 ---> 进入异常处理 ---> 拒绝签收丢弃消息 ---> 转发到死信队列
            //3. 手动签收
            channel.basicAck(deliveryTag,true);
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("出现异常，拒绝接受");
            //4.拒绝签收，不重回队列 requeue=false
            channel.basicNack(deliveryTag,true,false);
        }
    }

    /*********************测试延迟队列*********************/
    /**
     * 功能：下单后，10s未支付，取消订单，回滚库存
     * RabbitMQ没有提供延迟队列功能，但是可以使用 ： TTL + DLX 来实现延迟队列效果
     * 监听死信队列
     */
    @RabbitListener(queues = {RabbitMqConfiguration.DLX_QUEUE})
    public void handletestDelay(Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            // 1.接收转换消息
            System.out.println(new String(message.getBody()));

            // 2. 处理业务逻辑
            System.out.println("处理业务逻辑...");
            System.out.println("根据订单id查询其状态...");
            System.out.println("判断状态是否为支付成功");
            System.out.println("取消订单，回滚库存....");
            // 3. 手动签收
            channel.basicAck(deliveryTag,true);
        } catch (Exception e) {
            System.out.println("出现异常，拒绝接受");
            //4.拒绝签收，不重回队列 requeue=false
            channel.basicNack(deliveryTag,true,false);
        }
    }

}
