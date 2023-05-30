package com.wang.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.seckill.adapter.OrderAdapter;
import com.wang.seckill.adapter.UserAdapter;
import com.wang.seckill.entity.Order;
import com.wang.seckill.entity.User;
import com.wang.seckill.exception.GlobalException;
import com.wang.seckill.mapper.OrderMapper;
import com.wang.seckill.mapper.SeckillGoodsMapper;
import com.wang.seckill.service.IOrderService;
import com.wang.seckill.vo.GoodsVO;
import com.wang.seckill.vo.OrderDetailVO;
import com.wang.seckill.vo.ResponseEnum;
import com.wang.seckill.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

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
    @Autowired
    SeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    OrderAdapter orderAdapter;

    @Autowired
    UserAdapter userAdapter;

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
        order.setCreateDate(LocalDateTime.now());
        //插入订单
        this.save(order);
        return order;
    }

    @Override
    public OrderDetailVO generateOrderDetail(User user, Long orderId) {
        if(Objects.isNull(orderId)){
            throw new GlobalException(ResponseEnum.ORDER_ID_EMPTY);
        }
        //查询订单
        Order order = this.getBaseMapper().selectById(orderId);
        if(Objects.isNull(order)){
            throw new GlobalException(ResponseEnum.ORDER_NOT_EXIST);
        }
        OrderDetailVO orderDetailVO = new OrderDetailVO();
        orderDetailVO.setOrder(orderAdapter.orderToOrderDTO(order));

        Long goodsId = order.getGoodsId();
        GoodsVO goodsVO = seckillGoodsMapper.selectOneGoodsById(goodsId);
        orderDetailVO.setGoods(goodsVO);

        //待完善
        orderDetailVO.setUser(userAdapter.userToUserDTO(user));
        return orderDetailVO;
    }
}
