package com.wang.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.seckill.entity.Order;
import com.wang.seckill.entity.User;
import com.wang.seckill.mapper.OrderMapper;
import com.wang.seckill.service.IOrderService;
import com.wang.seckill.vo.GoodsVO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wang
 * @since 2022-02-19
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Override
    public Order generateOrder(User user, GoodsVO good) {
        Integer stockCount = good.getStockCount();
        Order order = new Order();
        order.setCreateDate(LocalDateTime.now());
        order.setOrderChannel(0);
        order.setGoodsCount(1);
        order.setGoodsId(good.getId());
        order.setGoodsName(good.getGoodsName());
        order.setGoodsPrice(good.getGoodsPrice());
        order.setUserId(user.getId());
        order.setDeliveryAddrId(0L);
        order.setStatus(0);
        this.save(order);
        return order;
    }
}
