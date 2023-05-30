package com.wang.seckill.service.impl;

import com.alibaba.fastjson2.JSON;
import com.wang.seckill.entity.*;
import com.wang.seckill.mapper.SeckillGoodsMapper;
import com.wang.seckill.rabbitmq.MQSender;
import com.wang.seckill.service.IGoodsService;
import com.wang.seckill.service.IOrderService;
import com.wang.seckill.service.ISeckillGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.seckill.service.ISeckillOrderService;
import com.wang.seckill.vo.GoodsVO;
import com.wang.seckill.vo.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.jws.WebParam;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
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
public class SeckillGoodsServiceImpl extends ServiceImpl<SeckillGoodsMapper, SeckillGoods> implements ISeckillGoodsService {
    @Autowired
    SeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    IOrderService orderService;

    @Override
    public List<GoodsVO> findGoods() {
        return seckillGoodsMapper.selectSeckillGoodsDetail();
    }

    @Override
    public GoodsVO findGoodsVoByID(Long goodsID){
        return seckillGoodsMapper.selectOneGoodsById(goodsID);
    }

}
