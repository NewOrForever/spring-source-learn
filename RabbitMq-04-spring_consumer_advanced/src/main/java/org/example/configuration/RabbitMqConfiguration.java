package org.example.configuration;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName:RabbitMqConfiguration
 * Package:org.example.configuration
 * Description: 配置是从{@link org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration} 中学来的
 *
 * @Date:2022/4/18 11:01
 * @Author:qs@1.com
 */
@Configuration
@EnableRabbit
@ComponentScan("org.example")
public class RabbitMqConfiguration {

    public static final String TEST_CONFIRM_EXCHANGE = "test_confirm_exchange";
    public static final String TEST_CONFIRM_QUEUE = "test_confirm_queue";
    public static final String TEST_DLX_QUEUE = "test_dlx_queue";
    public static final String DLX_QUEUE = "dlx_queue";

    @Bean
    public CachingConnectionFactory rabbitConnectionFactory() throws Exception {
        com.rabbitmq.client.ConnectionFactory connectionFactory = getRabbitConnectionFactoryBean().getObject();
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

    /**********@RabbitListener**********/
    @Bean
    public RabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // 手动签收消息 - 业务处理完后调用channel.basicAck()方法
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        factory.setConcurrentConsumers(2);
//        factory.setMaxConcurrentConsumers(2);
        // 一次消费2条，默认0拉取全部
        factory.setPrefetchCount(2);
        return factory;
    }


    // 定义一个RabbitMq的工具类
    @Bean
    public RabbitTemplate rabbitTemplate() throws Exception {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory());
        return rabbitTemplate;
    }

}
