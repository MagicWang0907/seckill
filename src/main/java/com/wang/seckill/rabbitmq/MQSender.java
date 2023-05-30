package com.wang.seckill.rabbitmq;

import com.wang.seckill.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MQSender {

    public final static String ROUTINE_KEY = "seckill.message";

    @Autowired
    RabbitTemplate rabbitTemplate;

    //向消息队列发送消息
    public void send(String message){
        log.info("send to rabbitMQ:" + message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE,ROUTINE_KEY,message);
    }

}
