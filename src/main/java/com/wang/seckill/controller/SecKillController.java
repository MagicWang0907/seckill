package com.wang.seckill.controller;

import com.alibaba.fastjson2.JSON;
import com.wang.seckill.entity.Order;
import com.wang.seckill.entity.SecKillMessage;
import com.wang.seckill.entity.User;
import com.wang.seckill.rabbitmq.MQSender;
import com.wang.seckill.service.IOrderService;
import com.wang.seckill.service.ISeckillGoodsService;
import com.wang.seckill.service.ISeckillOrderService;
import com.wang.seckill.vo.GoodsVO;
import com.wang.seckill.vo.ResponseBean;
import com.wang.seckill.vo.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.CopyOnWriteArraySet;

@Controller
@RequestMapping("/seckill")
@Api("秒杀业务管理")
public class SecKillController {
    @Autowired
    ISeckillGoodsService seckillGoodsService;

    @Autowired
    ISeckillOrderService seckillOrderService;

    @Autowired
    IOrderService orderService;

    @Autowired
    MQSender mqSender;

    //标记库存已经为0的商品ID
    CopyOnWriteArraySet<Long> EMPTY_STOCK_SET = new CopyOnWriteArraySet<>();

    @PostMapping("/doSeckill")
    @ResponseBody
    @ApiOperation("进行秒杀")
    public ResponseBean doSecKill(User user, Long goodsId) {
        //未登录返回登陆页面
        if (user == null) {
            return ResponseBean.error(ResponseEnum.SESSION_ERROR);
        }
        //库存不足，秒杀失败
        GoodsVO goodsVo = seckillGoodsService.findGoodsVoByID(goodsId);
        int count = goodsVo.getStockCount();
        if (EMPTY_STOCK_SET.contains(goodsId) || count < 1) {
            EMPTY_STOCK_SET.add(goodsId);
            return ResponseBean.error(ResponseEnum.EMPTY_STCOK);
        }
        //已经秒杀，不能重复秒杀
        if (seckillOrderService.alreadyDoSecKill(user, goodsId)) {
            return ResponseBean.error(ResponseEnum.REPEAT_ERROR);
        }

//        //条件满足开始秒杀,生成订单和秒杀订单
//        Order order = orderService.generateOrder(user, goodsVo);
//        seckillOrderService.generateSeckillOrder(order, count);
////            model.addAttribute("order",order);
////            model.addAttribute("goods",goodsVo);
        //使用异步队列进行下单
        SecKillMessage secKillMessage = new SecKillMessage(user, goodsVo);
        mqSender.send(JSON.toJSONString(secKillMessage));
        return ResponseBean.success(0);
    }

}
