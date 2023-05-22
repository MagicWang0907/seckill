package com.wang.seckill.controller;

import com.wang.seckill.entity.User;
import com.wang.seckill.service.ISeckillGoodsService;
import com.wang.seckill.service.IUserService;
import com.wang.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ISeckillGoodsService seckillGoodsService;

    @RequestMapping("/toList")
    public String toList(Model model, User user){
        if(user == null){
            return "login";
        }
        model.addAttribute(user);
        model.addAttribute("goodsList",seckillGoodsService.findGoods());
        return "goodsList";
    }

    @RequestMapping("/toDetail/{goodsId}")
    public String toDetail(Model model, User user, @PathVariable Long goodsId){
        model.addAttribute("user",user);
        GoodsVO goodsVo = seckillGoodsService.findGoodsVoByID(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date now = new Date();
        int secKillStatus = 0;
        int remainSeconds =0;
        if(now.before(startDate)){
            //秒杀还未开始
            remainSeconds = ((int) ((startDate.getTime()- now.getTime()) / 1000));
        }
        else if(now.after(endDate)){
            secKillStatus = 2 ;//秒杀结束
        }
        else{
            secKillStatus = 1;//秒杀正在进行
        }
        model.addAttribute("secKillStatus",secKillStatus);
        model.addAttribute("goods",goodsVo);
        model.addAttribute("remainSeconds",remainSeconds);
        return "goodsDetail";
    }

}