package com.example.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName:RabbitMqConfiguration
 * Package:com.example.config
 * Description:
 *
 * @Date:2022/4/19 16:42
 * @Author:qs@1.com
 */
@Configuration
public class RabbitMqConfiguration {
    @Bean
    public Exchange boot_topic_exchange() {
        return ExchangeBuilder.topicExchange("boot_topic_exchange").durable(true).build();
    }
    @Bean
    public Queue boot_topic_queue() {
        return QueueBuilder.durable("boot_topic_queue").build();
    }
    @Bean
    public Binding boot_topic_binding (@Qualifier("boot_topic_exchange") Exchange exchange,
                                       @Qualifier("boot_topic_queue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();
    }
}
