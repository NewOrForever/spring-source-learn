package org.example.producerConfirm;

import com.rabbitmq.client.*;
import org.example.BaseMQ;
import org.example.MyConstant;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ消息确认机制（生产者投递消息到broker的投递状态）-
 *  - 注意：这是producer和broker之间的消息投递情况，和消费者是否接收/确认消息无关
 *  - Confirm表示生产者将消息投递到broker时产生的状态
 *      - ack：broker已经将消息接收
 *      -nack：broker拒收消息，可能的原因有很多：队列已满、io异常、限流。。。
 *  - Return：代表消息被broker正常接收（ack），但broker没有对应的队列进行投递时产生状态，消息退回给生产者
 */
public class WeatherBureau extends BaseMQ {
    public static void main(String[] args) throws IOException, TimeoutException {

        Map area = new LinkedHashMap<String, String>();
        area.put("china.hunan.changsha.20201127", "中国湖南长沙20201127天气数据");
        area.put("china.hubei.wuhan.20201127", "中国湖北武汉20201127天气数据");
        area.put("china.hunan.zhuzhou.20201127", "中国湖南株洲20201128天气数据");
        area.put("us.cal.lsj.20201127", "美国加州洛杉矶20201127天气数据");

        area.put("china.hebei.shijiazhuang.20201128", "中国河北石家庄20201128天气数据");
        area.put("china.hubei.wuhan.20201128", "中国湖北武汉20201128天气数据");
        area.put("china.henan.zhengzhou.20201128", "中国河南郑州20201128天气数据");
        area.put("us.cal.lsj.20201128", "美国加州洛杉矶20201128天气数据");

        Connection connection = getConnection();
        // 创建通信“通道”，相当于TCP中的虚拟连接
        Channel channel = connection.createChannel();

        //  开启confirm监听模式
        channel.confirmSelect();
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                // 第二个参数代表接收的数据是否为批量接收，一般我们用不到
                System.out.println("消息已被broker接收，tag：" + deliveryTag + "，multiple：" + multiple);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("消息已被broker拒收，tag：" + deliveryTag);
            }
        });

        // 监听Return
        // basicPublish is called with "mandatory" or "immediate" flags set.
        channel.addReturnListener(new ReturnCallback() {
            @Override
            public void handle(Return returnMessage) {
                System.out.println("======================");
                System.out.println("Return编码：" + returnMessage.getReplyCode() + ", Return描述：" + returnMessage.getReplyText());
                System.out.println("Return交换机：" + returnMessage.getExchange() + ", Return路由：" + returnMessage.getRoutingKey());
                System.out.println("Return主题：" + new String(returnMessage.getBody()));
                System.out.println("======================");
            }
        });

        area.forEach((key, value) -> {

            try {
                // 第三个参数为：mandatory true代表如果消息无法正常投递则return回生产者，如果false，则直接将消息放弃
                channel.basicPublish(MyConstant.WEATHER_EXCHANGE_TOPIC, key.toString(), true, null, value.toString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        // 这里不要close，否则就无法进行监听了
       /* channel.close();
        connection.close();*/

    }

}
