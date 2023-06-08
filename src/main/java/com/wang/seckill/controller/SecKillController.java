package com.wang.seckill.controller;

import com.alibaba.fastjson2.JSON;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @PostMapping("/doSeckill")
    @ResponseBody
    @ApiOperation("进行秒杀")
    // TODO: 2023/6/4 add Redis cache
    public ResponseBean doSecKill(User user, Long goodsId) {
        //未登录返回登陆页面
        if (user == null) {
            return ResponseBean.error(ResponseEnum.SESSION_ERROR);
        }
        //库存不足，秒杀失败 本地缓存优化
        GoodsVO goodsVo = null;
        // TODO: 2023/6/4 将EmptyStock用Bean管理
        if(seckillOrderService.getEmptyStockMap().contains(goodsId)){
            return ResponseBean.error(ResponseEnum.EMPTY_STCOK);
        }
        else{
            goodsVo = seckillGoodsService.findGoodsVoByID(goodsId);
            int count = goodsVo.getStockCount();
            if (count < 1) {
                seckillOrderService.getEmptyStockMap().add(goodsId);
                return ResponseBean.error(ResponseEnum.EMPTY_STCOK);
            }
        }
        //已经秒杀，不能重复秒杀
        if (seckillOrderService.alreadyDoSecKill(user, goodsId)) {
            return ResponseBean.error(ResponseEnum.REPEAT_ERROR);
        }
        //使用异步队列进行下单
        SecKillMessage secKillMessage = new SecKillMessage(user, goodsVo);
        mqSender.send(JSON.toJSONString(secKillMessage));
        return ResponseBean.success(0);
    }

}
