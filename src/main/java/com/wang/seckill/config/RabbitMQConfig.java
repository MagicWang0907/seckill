package com.wang.seckill.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public final static String QUEUE = "seckill";

    public final static String EXCHANGE = "seckill_exchange";

    public final static String ROUTINE_KEY = "seckill.#";

    @Bean
    Queue queue(){
        return new Queue(QUEUE);
    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    Binding binding(){
        return BindingBuilder.bind(queue()).to(exchange()).with(ROUTINE_KEY);
    }

}
