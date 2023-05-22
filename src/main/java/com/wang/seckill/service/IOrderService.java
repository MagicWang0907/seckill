package com.wang.seckill.service;

import com.wang.seckill.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.seckill.entity.User;
import com.wang.seckill.vo.GoodsVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wang
 * @since 2022-02-19
 */
public interface IOrderService extends IService<Order> {
    Order generateOrder(User user, GoodsVO goods);

}
