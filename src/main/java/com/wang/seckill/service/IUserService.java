package com.wang.seckill.service;

import com.wang.seckill.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.seckill.vo.LoginVO;
import com.wang.seckill.vo.ResponseBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wang
 * @since 2022-02-13
 */
public interface IUserService extends IService<User> {

    ResponseBean doLogin(LoginVO loginVO, HttpServletRequest req, HttpServletResponse resp);

    User getUserByCookie(String ticket);
}
