package com.wang.seckill.controller;


import com.wang.seckill.entity.User;
import com.wang.seckill.service.IOrderService;
import com.wang.seckill.vo.OrderDetailVO;
import com.wang.seckill.vo.ResponseBean;
import com.wang.seckill.vo.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wang
 * @since 2022-02-19
 */
@Controller
@RequestMapping("/order")
@Api("订单功能管理")
public class OrderController {

    @Autowired
    IOrderService orderService;

    @GetMapping("/detail")
    @ApiOperation("获取订单详情")
    @ResponseBody
    public ResponseBean detail(User user, Long orderId){
        if(user == null){
            return ResponseBean.error(ResponseEnum.SESSION_ERROR);
        }
        //userVO传前端待扩展 user转userVO
        OrderDetailVO orderDetailVO = orderService.generateOrderDetail(user, orderId);
        return ResponseBean.success(orderDetailVO);
    }


}
