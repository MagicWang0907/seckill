package com.wang.seckill.service;

import com.wang.seckill.entity.Order;
import com.wang.seckill.entity.SeckillOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.seckill.entity.User;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wang
 * @since 2022-02-19
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {

    boolean alreadyDoSecKill(User user,Long goodsId);

    int generateSeckillOrder(Order order);

    String getSecKillOrderResult(User user, Long orderId);

    CopyOnWriteArraySet<Long> getEmptyStockMap();
}
