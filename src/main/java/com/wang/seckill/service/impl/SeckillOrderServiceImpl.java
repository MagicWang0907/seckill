package com.wang.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wang.seckill.entity.Order;
import com.wang.seckill.entity.SeckillGoods;
import com.wang.seckill.entity.SeckillOrder;
import com.wang.seckill.entity.User;
import com.wang.seckill.mapper.SeckillGoodsMapper;
import com.wang.seckill.mapper.SeckillOrderMapper;
import com.wang.seckill.service.ISeckillGoodsService;
import com.wang.seckill.service.ISeckillOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wang
 * @since 2022-02-19
 */
@Service
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements ISeckillOrderService {

    @Autowired
    SeckillOrderMapper seckillOrderMapper;

    @Autowired
    SeckillGoodsMapper seckillGoodsMapper;

    @Override
    public boolean alreadyDoSecKill(User user, Long goodsId) {
        QueryWrapper<SeckillOrder> seckillOrderQueryWrapper = new QueryWrapper<>();
        seckillOrderQueryWrapper.eq("user_id",user.getId()).eq("goods_id",goodsId);
        SeckillOrder seckillOrder = seckillOrderMapper.selectOne(seckillOrderQueryWrapper);
        if (seckillOrder!=null){
            return true;
        }
        return false;
    }

    @Override
    public SeckillOrder generateSeckillOrder( Order order, int goodCount) {
        //减库存
        SeckillGoods good= seckillGoodsMapper.selectOne(new QueryWrapper<SeckillGoods>().eq("goods_id", order.getGoodsId()));
        good.setStockCount(good.getStockCount()-1);
        seckillGoodsMapper.updateById(good);
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(order.getUserId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(order.getGoodsId());
        seckillOrderMapper.insert(seckillOrder);
        return seckillOrder;
    }
}
