package com.wang.seckill.controller;


import com.wang.seckill.entity.User;
import com.wang.seckill.service.ISeckillOrderService;
import com.wang.seckill.vo.ResponseBean;
import com.wang.seckill.vo.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * @author wang
 * @since 2022-02-19
 */
@Controller
@RequestMapping("/seckillOrder")
@Api("秒杀订单信息管理")
public class SeckillOrderController {

    @Autowired
    ISeckillOrderService seckillOrderService;

    @GetMapping("/getResult")
    @ResponseBody
    @ApiOperation("客户端轮训查询秒杀订单状态")
    public ResponseBean getSecKillOrderResult(User user, Long goodsId) {
        if(user == null){
            return ResponseBean.error(ResponseEnum.SESSION_ERROR);
        }
        if(Objects.isNull(goodsId)){
            return ResponseBean.error(ResponseEnum.GOOD_ID_EMPTY);
        }

        return ResponseBean.success(seckillOrderService.getSecKillOrderResult(user,goodsId));
    }

}
