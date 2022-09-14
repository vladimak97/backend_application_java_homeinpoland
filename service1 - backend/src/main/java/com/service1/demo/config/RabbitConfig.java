package com.service1.demo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//generate bean definitions and service requests for those beans at runtime
@Configuration

//RabbitMQ Configuration - microservice based architecture - task queue
//@Bean - method produces a bean to be managed by the Spring container

public class RabbitConfig {
    @Bean
    public Queue registrationQueue(){
        Queue queue = new Queue("registrationStatus", false);
        return queue;
    }
}

