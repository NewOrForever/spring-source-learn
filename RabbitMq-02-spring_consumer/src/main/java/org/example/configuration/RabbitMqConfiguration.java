package org.example.configuration;

import org.example.listener.SpringQueueListener;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.ListenerContainerFactoryBean;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
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
@EnableRabbit
@ComponentScan("org.example")
public class RabbitMqConfiguration {
    public static final String SPRING_QUEUE = "spring_queue";

    public static final String SPRING_WORK_QUEUE = "spring_work_queue";

    public static final String SPRING_PUBSUB_QUEUE_01 = "spring_pubsub_queue_01";
    public static final String SPRING_PUBSUB_QUEUE_02 = "spring_pubsub_queue_02";

    public static final String SPRING_ROUTING_QUEUE_01 = "spring_routing_queue01";
    public static final String SPRING_ROUTING_QUEUE_02 = "spring_routing_queue02";

    public static final String SPRING_TOPIC_QUEUE_01 = "spring_topic_queue_01";
    public static final String SPRING_TOPIC_QUEUE_02 = "spring_topic_queue_02";

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

    /************************添加Listener************************/
    /**
     * 下面这些配置可以的啊，但是好像太繁琐了，我每一个MessageListener都要写一个类然后还要@Bean一个MessageListenerContainer
     * 多了的话配置类就乱得嘞 ---> 改用@RabbitListener注解
     */
    // 消息监听
//    @Bean
//    public SpringQueueListener springQueueListener() {
//        return new SpringQueueListener();
//    }
    // ListenerContainer
//    @Bean
//    public ListenerContainerFactoryBean listenerContainerFactoryBean() throws Exception {
//        ListenerContainerFactoryBean listenerContainerFactoryBean = new ListenerContainerFactoryBean();
//        listenerContainerFactoryBean.setConnectionFactory(rabbitConnectionFactory());
//
//        // 添加
//        listenerContainerFactoryBean.setMessageListener(springQueueListener());
//        listenerContainerFactoryBean.setQueueNames(SPRING_QUEUE);
//
//        return listenerContainerFactoryBean;
//    }
//    @Bean
//    public MessageListenerContainer simpleMessageListenerContainer() throws Exception {
//        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(rabbitConnectionFactory());
//        simpleMessageListenerContainer.setQueueNames(SPRING_QUEUE);
//        simpleMessageListenerContainer.setMessageListener(springQueueListener());
//
//        return simpleMessageListenerContainer;
//    }


    /**********@RabbitListener**********/
    @Bean
    public RabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
//        factory.setConcurrentConsumers(2);
//        factory.setMaxConcurrentConsumers(2);
        return factory;
    }


    // 定义一个RabbitMq的工具类
    @Bean
    public RabbitTemplate rabbitTemplate() throws Exception {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory());
        return rabbitTemplate;
    }

}
