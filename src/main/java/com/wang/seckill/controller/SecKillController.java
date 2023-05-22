package com.wang.seckill.controller;

import com.wang.seckill.entity.User;
import com.wang.seckill.service.ISeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seckill")
public class SecKillController {
    @Autowired
    ISeckillGoodsService seckillGoodsService;

    @RequestMapping("/doSeckill")
    public String doSecKill(Model model , User user,Long goodsId){
        return seckillGoodsService.doSecKill(model,user,goodsId);
    }
}
