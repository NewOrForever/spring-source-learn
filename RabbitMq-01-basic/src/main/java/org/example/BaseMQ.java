package org.example;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class BaseMQ {

//    private static final String CONNECT_HOST = "192.168.0.110";
    private static final String CONNECT_HOST = "192.168.65.227";
    private static final Integer CONNECT_PORT = 5672;
    private static final String CONNECT_USER_NAME = "sq";
    private static final String CONNECT_PASSWORD = "123456";
    private static final String MY_VIRTUAL_HOST = "/sq_vh";
    private static final ConnectionFactory connectionFactory = new ConnectionFactory();
    static {
        connectionFactory.setHost(CONNECT_HOST);
        // 5672是rabbit默认的端口号
        connectionFactory.setPort(CONNECT_PORT);
        connectionFactory.setUsername(CONNECT_USER_NAME);
        connectionFactory.setPassword(CONNECT_PASSWORD);
        connectionFactory.setVirtualHost(MY_VIRTUAL_HOST);
    }

    protected static Connection getConnection() {
        // 获取TCP长连接
        Connection connection = null;
        try {
            connection = connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static String getMyVirtualHost() {
        return MY_VIRTUAL_HOST;
    }
}
