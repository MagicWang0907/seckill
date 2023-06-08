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

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

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

    @Autowired
    ISeckillGoodsService seckillGoodsService;

    //标记库存已经为0的商品ID
    CopyOnWriteArraySet<Long> EMPTY_STOCK_SET = new CopyOnWriteArraySet<>();

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
    public int generateSeckillOrder( Order order) {
        //减库存
        UpdateWrapper<SeckillGoods> sql = new UpdateWrapper<SeckillGoods>()
                .setSql("stock_count=stock_count-1")
                .eq("goods_id", order.getGoodsId())
                .gt("stock_count", 0);
        boolean isEmptyStock = seckillGoodsService.update(sql);
        if(!isEmptyStock){
            return 0;
        }
        //生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(order.getUserId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(order.getGoodsId());
        int res = 0;
        try {
            res = seckillOrderMapper.insert(seckillOrder);
        }finally {
            if(res == 0){
                //生成订单失败，可能情况订单重复
                seckillGoodsService.update(new UpdateWrapper<SeckillGoods>()
                        .setSql("stock_count=stock_count+1")
                        .eq("goods_id", order.getGoodsId()));
            }
        }



        return res;
    }
    /*
        返回orderID成功，返回-1 秒杀失败，返回0 排队中
     */
    @Override
    public String getSecKillOrderResult(User user, Long goodsId) {
        String res = "0";
        SeckillOrder seckillOrder = seckillOrderMapper.selectOne(new QueryWrapper<SeckillOrder>().eq("goods_id", goodsId).eq("user_id", user.getId()));
        if(!Objects.isNull(seckillOrder)){
            res = seckillOrder.getOrderId().toString();
        }
        else{
            // TODO: 2023/5/30 使用Redis优化此处查询 
            if(seckillGoodsMapper.selectOne(new QueryWrapper<SeckillGoods>().eq("goods_id", goodsId)).getStockCount()<=0){
                res = "-1";
            }
        }
        return res;
    }

    @Override
    public CopyOnWriteArraySet<Long> getEmptyStockMap() {
        return EMPTY_STOCK_SET;
    }
}
