package org.example.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.example.BaseMQ;
import org.example.MyConstant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发布订阅模式
 * 气象站发布气象信息，门户网站接收
 */
public class WeatherBureau extends BaseMQ {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = getConnection();

        // 创建通信“通道”，相当于TCP中的虚拟连接
        Channel channel = connection.createChannel();

        //四个参数
        //exchange 交换机
        //队列名称 发布订阅模式用不到
        //额外的设置属性
        //最后一个参数是要传递的消息字节数组
        channel.basicPublish(MyConstant.WEATHER_EXCHANGE, "", null, "今天天气是晴天".getBytes());

        channel.close();
        connection.close();

    }

}
