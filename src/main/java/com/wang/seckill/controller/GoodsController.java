package com.wang.seckill.controller;

import com.wang.seckill.entity.User;
import com.wang.seckill.service.ISeckillGoodsService;
import com.wang.seckill.service.IUserService;
import com.wang.seckill.vo.GoodDetailVO;
import com.wang.seckill.vo.GoodsVO;
import com.wang.seckill.vo.ResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/goods")
@Api("商品信息管理")
public class GoodsController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ISeckillGoodsService seckillGoodsService;

    @GetMapping("/toList")
    @ApiOperation("获取所有商品列表")
    public String toList(Model model, User user){
        if(user == null){
            return "login";
        }
        model.addAttribute(user);
        model.addAttribute("goodsList",seckillGoodsService.findGoods());
        return "goodsList";
    }

    @GetMapping("/toDetail/{goodsId}")
    @ResponseBody
    @ApiOperation("根据商品ID获取对应的商品详情")
    public ResponseBean toDetail(User user, @PathVariable Long goodsId){
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
            remainSeconds = -1;
        }
        else{
            secKillStatus = 1;//秒杀正在进行
        }
        GoodDetailVO goodDetailVO = new GoodDetailVO();
        goodDetailVO.setGoods(goodsVo);
        goodDetailVO.setUser(user);
        goodDetailVO.setRemainSeconds(remainSeconds);
        goodDetailVO.setSecKillStatus(secKillStatus);
        return ResponseBean.success(goodDetailVO);
    }

}
