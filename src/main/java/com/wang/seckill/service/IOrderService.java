package com.wang.seckill.service;

import com.wang.seckill.dto.UserDTO;
import com.wang.seckill.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.seckill.entity.User;
import com.wang.seckill.vo.GoodsVO;
import com.wang.seckill.vo.OrderDetailVO;


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

    //生成订单详情
    OrderDetailVO generateOrderDetail(User user, Long orderId);

    public boolean deleteOrderById(Long id);
}
