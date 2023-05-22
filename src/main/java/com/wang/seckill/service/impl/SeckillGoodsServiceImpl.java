package com.wang.seckill.service.impl;

import com.wang.seckill.entity.Order;
import com.wang.seckill.entity.SeckillGoods;
import com.wang.seckill.entity.SeckillOrder;
import com.wang.seckill.entity.User;
import com.wang.seckill.mapper.SeckillGoodsMapper;
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
import java.util.List;

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
    ISeckillOrderService seckillOrderService;

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

    @Override
    public String doSecKill(Model model, User user, Long goodsId) {
        //未登录返回登陆页面
        if(user == null){
            return "login";
        }
        //已经秒杀，不能重复秒杀
        if(seckillOrderService.alreadyDoSecKill(user,goodsId)){
            model.addAttribute("errmsg", ResponseEnum.REPEAT_ERROR);
            return "seckillFailure";
        }
        GoodsVO goodsVo = this.findGoodsVoByID(goodsId);
        //库存不足，秒杀失败
        int count = goodsVo.getStockCount();
        if(count<1){
            model.addAttribute("errmsg",ResponseEnum.EMPTY_STCOK);
            return "seckillFailure";
        }
        //条件满足开始秒杀,生成订单和秒杀订单
        Order order = orderService.generateOrder(user, goodsVo);
        seckillOrderService.generateSeckillOrder(order, count);
        model.addAttribute("order",order);
        model.addAttribute("goods",goodsVo);
        return "orderDetail";
    }
}
