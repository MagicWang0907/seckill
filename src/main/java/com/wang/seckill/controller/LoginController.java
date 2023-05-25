package com.wang.seckill.controller;

import com.wang.seckill.service.IUserService;
import com.wang.seckill.vo.LoginVO;
import com.wang.seckill.vo.ResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
@Api("登录功能")
@Slf4j
public class LoginController {

    @Autowired
    private IUserService userService;

    @GetMapping("/toLogin")
    @ApiOperation("跳转登录界面")
    public String toLogin(){
        return "login";
    }

    @PostMapping("/doLogin")
    @ApiOperation("登录")
    @ResponseBody
    public ResponseBean doLogin(@Valid LoginVO loginVO, HttpServletRequest req, HttpServletResponse resp){

        return userService.doLogin(loginVO,req,resp);
    }

}
