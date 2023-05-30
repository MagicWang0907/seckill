package com.wang.seckill.adapter;

import com.wang.seckill.dto.OrderDTO;
import com.wang.seckill.entity.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Component
public class OrderAdapter {

    public OrderDTO orderToOrderDTO(Order order){
        return OrderDTO.builder()
                .orderChannel(order.getOrderChannel())
                .goodsCount(order.getGoodsCount())
                .createDate(toDate(order.getCreateDate()))
                .deliveryAddrId(order.getDeliveryAddrId())
                .goodsName(order.getGoodsName())
                .goodsPrice(order.getGoodsPrice())
                .id(order.getId())
                .payDate(toDate(order.getPayDate()))
                .status(order.getStatus())
                .goodsId(order.getGoodsId())
                .userId(order.getUserId())
                .build();
    }

    private Date toDate(LocalDateTime localDateTime) {
        if(Objects.isNull(localDateTime)){
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


}
