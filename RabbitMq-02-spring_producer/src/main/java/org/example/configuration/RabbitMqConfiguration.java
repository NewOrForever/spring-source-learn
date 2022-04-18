package org.example.configuration;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.time.Duration;

/**
 * ClassName:RabbitMqConfiguration
 * Package:org.example.configuration
 * Description: 配置是从{@link org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration} 中学来的
 *
 * @Date:2022/4/18 11:01
 * @Author:qs@1.com
 */
@Configuration
//@ComponentScan("com.example")
public class RabbitMqConfiguration {
    public static final String SPRING_QUEUE = "spring_queue";

    @Bean
    public CachingConnectionFactory rabbitConnectionFactory() throws Exception {
        ConnectionFactory connectionFactory = getRabbitConnectionFactoryBean().getObject();
        connectionFactory.setHost("192.168.65.227");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("sq");
        connectionFactory.setPassword("123456");
        connectionFactory.setVirtualHost("/sq_vh");
        CachingConnectionFactory factory = new CachingConnectionFactory(connectionFactory);

        return factory;
    }

    private RabbitConnectionFactoryBean getRabbitConnectionFactoryBean() {
        RabbitConnectionFactoryBean factory = new RabbitConnectionFactoryBean();
        factory.afterPropertiesSet();
        return factory;
    }

    // 会在RabbitAdmin的初始化方法中自动声明定义管理交换机、队列、binding
    // 详细可以看下源码
    @Bean
    public AmqpAdmin amqpAdmin(org.springframework.amqp.rabbit.connection.ConnectionFactory rabbitConnectionFactory) {
        return new RabbitAdmin(rabbitConnectionFactory);
    }

    // 测试helloworld
    // 定义一个队列
    @Bean
    public Queue spring_queue() {
        return new Queue(SPRING_QUEUE, false, false, false, null);
    }


    // 定义一个RabbitMq的工具类
    @Bean
    public RabbitTemplate rabbitTemplate() throws Exception {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory());
        return rabbitTemplate;
    }

}
