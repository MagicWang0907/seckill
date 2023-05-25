package com.wang.seckill.controller;

import com.wang.seckill.entity.User;
import com.wang.seckill.service.ISeckillGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seckill")
@Api("秒杀业务管理")
public class SecKillController {
    @Autowired
    ISeckillGoodsService seckillGoodsService;

    @GetMapping("/doSeckill")
    @ApiOperation("进行秒杀")
    public String doSecKill(Model model , User user,Long goodsId){
        return seckillGoodsService.doSecKill(model,user,goodsId);
    }
}
