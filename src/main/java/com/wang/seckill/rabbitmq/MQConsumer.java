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

import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
@Slf4j
public class MQConsumer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    IOrderService orderService;

    @Autowired
    ISeckillOrderService seckillOrderService;

    @Autowired
    ISeckillGoodsService seckillGoodsService;

    @RabbitListener(queues = "seckill")
    public void consumer(String message){
        SecKillMessage secKillMessage = JSON.parseObject(message, SecKillMessage.class);
        //库存是否足够
        CopyOnWriteArraySet<Long> emptyStockMap = seckillOrderService.getEmptyStockMap();
        Long goodsId = secKillMessage.getGoodsVO().getId();
        boolean isEmpty = emptyStockMap.contains(goodsId);
        if(isEmpty){
            return;
        }
        if(seckillGoodsService.getById(goodsId).getStockCount()<1){
            emptyStockMap.add(goodsId);
            return;
        };
        //开始下单
        Order order = orderService.generateOrder(secKillMessage.getUser(), secKillMessage.getGoodsVO());
        if(Objects.isNull(order)){
            emptyStockMap.add(goodsId);
            return;
        }
        int res = 0;
        try {
            res = seckillOrderService.generateSeckillOrder(order);
        }
        finally {
            //如果失败
            if(res == 0){
                Long id = order.getId();
                orderService.deleteOrderById(id);
                log.info("订单"+id+"秒杀失败");
                seckillOrderService.getEmptyStockMap().remove(id);
                //删除之前订单
                orderService.deleteOrderById(order.getId());
            }
        }
        return;
    }
}
