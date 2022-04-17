package org.example.workQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.example.BaseMQ;
import org.example.MyConstant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class OrderService extends BaseMQ {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = getConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(MyConstant.WORK_QUEUE, false, false, false, null);
        for (int i = 0; i < 100; i++) {
            channel.basicPublish("", MyConstant.WORK_QUEUE, null, ("订单" + i + "创建成功").getBytes());
        }

        System.out.println("发送数据成功");
        channel.close();
        connection.close();
    }
}
