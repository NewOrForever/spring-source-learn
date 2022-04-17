package org.example.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.example.BaseMQ;
import org.example.MyConstant;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Topic通配符模式
 * - 交换机要选择topic
 * - 和routing模式的不同就是rouingKey是模糊匹配
 *  - * 匹配一个词：com.* ---> com.exam匹配，com.exam.xxxxx不匹配
 *  - # 匹配一个或多个词
 * 气象站发布气象信息，门户网站接受
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

        area.forEach((key, value) -> {
            //四个参数
            //exchange 交换机
            //routingKey
            //额外的设置属性
            //最后一个参数是要传递的消息字节数组
            try {
                channel.basicPublish(MyConstant.WEATHER_EXCHANGE_TOPIC, key.toString(), null, value.toString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        channel.close();
        connection.close();

    }

}
