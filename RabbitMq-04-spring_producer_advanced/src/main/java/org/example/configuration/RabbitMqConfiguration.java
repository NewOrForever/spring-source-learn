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
import java.util.HashMap;
import java.util.Map;

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

    public static final String TEST_CONFIRM_EXCHANGE = "test_confirm_exchange";
    public static final String TEST_CONFIRM_QUEUE = "test_confirm_queue";

    public static final String TEST_TTL_EXCHANGE = "test_ttl_exchange";
    public static final String TEST_TTL_QUEUE = "test_ttl_queue";

    public static final String TEST_DLX_EXCHANGE = "test_dlx_exchange";
    public static final String TEST_DLX_QUEUE = "test_dlx_queue";
    public static final String DLX_EXCHANGE = "dlx_exchange";
    public static final String DLX_QUEUE = "dlx_queue";

    @Bean
    public CachingConnectionFactory rabbitConnectionFactory() throws Exception {
        ConnectionFactory connectionFactory = getRabbitConnectionFactoryBean().getObject();
        connectionFactory.setHost("192.168.65.227");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("sq");
        connectionFactory.setPassword("123456");
        connectionFactory.setVirtualHost("/sq_vh");
        CachingConnectionFactory factory = new CachingConnectionFactory(connectionFactory);

        // 开启消息的confirm模式
        factory.setPublisherConfirms(true);
        // 开启消息退回模式 - 交换机正常接收到了消息但找不到可路由的队列
        // mandatory若为true，则交换机会回退到producer
        factory.setPublisherReturns(true);

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

    /*************************消息可靠性投递的测试***************************/
    @Bean
    public Exchange test_confirm_exchange() {
        return ExchangeBuilder.directExchange(TEST_CONFIRM_EXCHANGE).durable(true).build();
    }
    @Bean
    public Queue test_confirm_queue() {
        return QueueBuilder.durable(TEST_CONFIRM_QUEUE).build();
    }
    @Bean
    public Binding test_confirm_binding() {
        return BindingBuilder.bind(test_confirm_queue()).to(test_confirm_exchange()).with("confirm").noargs();
    }

    /*********************测试ttl消息过期**********************/
    @Bean
    public Exchange test_ttl_exchange() {
        return ExchangeBuilder.topicExchange(TEST_TTL_EXCHANGE).durable(true).build();
    }
    @Bean
    public Queue test_ttl_queue() {
        Map<String, Object> map = new HashMap<>();
        // 消息过期时间
        map.put("x-message-ttl", 10 * 1000);
        // 队列过期时间
//        map.put("x-expires", 10 * 1000);
        return QueueBuilder.durable(TEST_TTL_QUEUE).withArguments(map).build();
    }
    @Bean
    public Binding test_ttl_binding() {
        return BindingBuilder.bind(test_ttl_queue()).to(test_ttl_exchange()).with("ttl.#").noargs();
    }

    /************************测试死信队列**************************/
    /**
     * 测试：我发个12条数据来验证max-length和ttl
     * 死信队列：
     *  1. 声明正常的队列(test_queue_dlx)和交换机(test_exchange_dlx)
     *  2. 声明死信队列(queue_dlx)和死信交换机(exchange_dlx)
     *  3. 正常队列绑定死信交换机
     *      设置两个参数：
     *          * x-dead-letter-exchange：死信交换机名称
     *          * x-dead-letter-routing-key：发送给死信交换机的routingkey
     */
    // 普通交换机
    @Bean
    public Exchange test_dlx_exchange() {
        return ExchangeBuilder.topicExchange(TEST_DLX_EXCHANGE).durable(true).build();
    }
    // 普通队列
    @Bean
    public Queue test_dlx_queue() {
        Map<String, Object> map = new HashMap<>();
        // 死信交换机
        map.put("x-dead-letter-exchange", DLX_EXCHANGE);
        // 普通队列发送给死信交换机的routingKey
        map.put("x-dead-letter-routing-key", "dlx.routing.1");
        // 消息过期时间
        map.put("x-message-ttl", 10 * 1000);
        // 设置队列的长度限制（能放消息个数）
        map.put("x-max-length", 10);
        return QueueBuilder.durable(TEST_DLX_QUEUE).withArguments(map).build();
    }
    // 普通绑定
    @Bean
    public Binding test_dlx_binding() {
        return BindingBuilder.bind(test_dlx_queue()).to(test_dlx_exchange()).with("dlx.routing.#").noargs();
    }
    // 死信交换机
    @Bean
    public Exchange dlx_exchange() {
        return ExchangeBuilder.topicExchange(DLX_EXCHANGE).durable(true).build();
    }
    // 死信队列
    @Bean
    public Queue dlx_queue() {
        return QueueBuilder.durable(DLX_QUEUE).build();
    }
    // 死信交换机和死信队列绑定
    @Bean
    public Binding dlx_binding() {
        return BindingBuilder.bind(dlx_queue()).to(dlx_exchange()).with("dlx.routing.#").noargs();
    }


    // 定义一个RabbitMq的工具类
    @Bean
    public RabbitTemplate rabbitTemplate() throws Exception {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory());
        // publisher-returns="true" 开启 退回模式
        // 使用rabbitTemplate.setReturnCallback设置退回函数，当消息从exchange路由
        // 到queue失败后，如果设置了rabbitTemplate.setMandatory(true)参数，则会将
        // 消息退回给producer。并执行回调函数returnedMessage。
        rabbitTemplate.setMandatory(true);
        return rabbitTemplate;
    }

}
