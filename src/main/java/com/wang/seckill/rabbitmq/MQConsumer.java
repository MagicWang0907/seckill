package com.wang.seckill.rabbitmq;

import com.alibaba.fastjson2.JSON;
import com.wang.seckill.entity.Order;
import com.wang.seckill.entity.SecKillMessage;
import com.wang.seckill.service.IOrderService;
import com.wang.seckill.service.ISeckillGoodsService;
import com.wang.seckill.service.ISeckillOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQConsumer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    IOrderService orderService;

    @Autowired
    ISeckillOrderService seckillOrderService;

    @RabbitListener(queues = "seckill")
    public void consumer(String message){
        SecKillMessage secKillMessage = JSON.parseObject(message, SecKillMessage.class);
        //开始下单
        Order order = orderService.generateOrder(secKillMessage.getUser(), secKillMessage.getGoodsVO());
        seckillOrderService.generateSeckillOrder(order);
        return;
    }
}
