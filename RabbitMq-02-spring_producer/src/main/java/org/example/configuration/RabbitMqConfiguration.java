package org.example.configuration;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.*;
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
    public static final String SPRING_WORK_QUEUE = "spring_work_queue";
    public static final String SPRING_PUBSUB_EXCHANGE = "spring_pubsub_exchange";
    public static final String SPRING_PUBSUB_QUEUE_01 = "spring_pubsub_queue_01";
    public static final String SPRING_PUBSUB_QUEUE_02= "spring_pubsub_queue_02";

    public static final String SPRING_ROUTING_EXCHANGE = "spring_routing_exchange";
    public static final String SPRING_ROUTING_QUEUE_01 = "spring_routing_queue01";
    public static final String SPRING_ROUTING_QUEUE_02 = "spring_routing_queue02";

    public static final String SPRING_TOPIC_EXCHANGE = "spring_topic_exchange";
    public static final String SPRING_TOPIC_QUEUE_01 = "spring_topic_queue_01";
    public static final String SPRING_TOPIC_QUEUE_02 = "spring_topic_queue_02";

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
        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitConnectionFactory);
        return rabbitAdmin;
    }

    // 测试helloworld
    // 定义一个队列
    @Bean
    public Queue spring_queue() {
        return new Queue(SPRING_QUEUE, false, false, false, null);
    }

    // 测试workqueue
    // 定义一个队列
    @Bean
    public Queue spring_work_queue() {
        return new Queue(SPRING_WORK_QUEUE, false, false, false, null);
    }

    /*************** 测试pubsub ***************/
    // 定义一个交换机
    @Bean
    public Exchange spring_pubsub_exchange() {
        // FanoutExchange fanoutExchange = new FanoutExchange(SPRING_PUBSUB_EXCHANGE, false, false, null);
        return ExchangeBuilder.fanoutExchange(SPRING_PUBSUB_EXCHANGE).durable(true).build();
    }
    // 定义队列
    @Bean
    public Queue spring_pubsub_queue01() {
        return QueueBuilder.durable(SPRING_PUBSUB_QUEUE_01).build();
    }
    @Bean
    public Queue spring_pubsub_queue02() {
        return QueueBuilder.durable(SPRING_PUBSUB_QUEUE_02).build();
    }
    // binding交换机和队列
    @Bean
    public Binding pubSubBinding01() {
        return BindingBuilder.bind(spring_pubsub_queue01()).to(spring_pubsub_exchange()).with("").noargs();
    }
    @Bean
    public Binding pubSubBinding02() {
        return BindingBuilder.bind(spring_pubsub_queue02()).to(spring_pubsub_exchange()).with("").noargs();
    }

    /***************测试routing***************/
    @Bean
    public Exchange spring_routing_exchange() {
        return ExchangeBuilder.directExchange(SPRING_ROUTING_EXCHANGE).durable(true).build();
    }
    // 定义一个队列
    @Bean
    public Queue spring_routing_queue01() {
        return QueueBuilder.durable(SPRING_ROUTING_QUEUE_01).build();
    }
    @Bean
    public Queue spring_routing_queue02() {
        return QueueBuilder.durable(SPRING_ROUTING_QUEUE_02).build();
    }
    @Bean
    public Binding routingBinding01() {
        return BindingBuilder.bind(spring_routing_queue01()).to(spring_routing_exchange()).with("routing0").noargs();
    }
    @Bean
    public Binding routingBinding02() {
        return BindingBuilder.bind(spring_routing_queue02()).to(spring_routing_exchange()).with("routing1").noargs();
    }

    /*************** 测试topic ******************/
    @Bean
    public Exchange spring_topic_exchange() {
        return ExchangeBuilder.topicExchange(SPRING_TOPIC_EXCHANGE).durable(true).build();
    }
    @Bean
    public Queue spring_topic_queue01() {
        return QueueBuilder.durable(SPRING_TOPIC_QUEUE_01).build();
    }
    @Bean
    public Queue spring_topic_queue02() {
        return QueueBuilder.durable(SPRING_TOPIC_QUEUE_02).build();
    }
    @Bean
    public Binding spring_topic_binding01() {
        return BindingBuilder.bind(spring_topic_queue01()).to(spring_topic_exchange()).with("routing.#").noargs();
    }
    @Bean
    public Binding spring_topic_binding02() {
        return BindingBuilder.bind(spring_topic_queue02()).to(spring_topic_exchange()).with("#.binding").noargs();
    }



    // 定义一个RabbitMq的工具类
    @Bean
    public RabbitTemplate rabbitTemplate() throws Exception {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory());
        return rabbitTemplate;
    }

}
