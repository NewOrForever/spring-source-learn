package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RabbitMq03BootProducerApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testProducer() {
        for (int i = 0; i < 5; i++) {
            rabbitTemplate.convertAndSend("boot_topic_exchange", "boot." + i, "boot." + i);
        }
        for (int i = 0; i < 5; i++) {
            rabbitTemplate.convertAndSend("boot_topic_exchange", "springboot" + i, "springboot" + i);
        }
    }

}
