package com.wang.seckill.service;

import com.wang.seckill.entity.Order;
import com.wang.seckill.entity.SeckillOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.seckill.entity.User;

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

    SeckillOrder generateSeckillOrder(Order order);

    Long getSecKillOrderResult(User user, Long orderId);

}
