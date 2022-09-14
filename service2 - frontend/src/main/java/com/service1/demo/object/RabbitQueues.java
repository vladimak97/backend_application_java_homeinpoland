package com.service1.demo.object;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitQueues {
    @Bean
    public Queue registrationQueue(){
        Queue queue = new Queue("registrationStatus", false);
        return queue;
    }
}
