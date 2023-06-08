package com.wang.seckill.service.impl;

import com.wang.seckill.entity.User;
import com.wang.seckill.exception.GlobalException;
import com.wang.seckill.mapper.UserMapper;
import com.wang.seckill.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.seckill.utils.CookieUtil;
import com.wang.seckill.utils.MD5Utils;
import com.wang.seckill.utils.UUIDUtil;
import com.wang.seckill.vo.LoginVO;
import com.wang.seckill.vo.ResponseBean;
import com.wang.seckill.vo.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wang
 * @since 2022-02-13
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;


    @Override
    public ResponseBean doLogin(LoginVO loginVO, HttpServletRequest req, HttpServletResponse resp) {
        String mobile = loginVO.getMobile();
        String password = loginVO.getPassword();
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new GlobalException(ResponseEnum.LOGIN_ERROR);
        }

        User user = userMapper.selectById(mobile);
        if (user==null|| !MD5Utils.fromPassToDBPass(password,user.getSlat()).equals(user.getPassword())){
            throw new GlobalException(ResponseEnum.LOGIN_ERROR);
        }
        String ticket = UUIDUtil.uuid();
        //req.getSession().setAttribute(ticket,user);
        redisTemplate.opsForValue().set("user:"+ticket,user);
        CookieUtil.setCookie(req,resp,"userTicket",ticket);

        return ResponseBean.success(ticket);
    }

    @Override
    public User getUserByCookie(String ticket) {
        if(StringUtils.isEmpty(ticket)){
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:"+ticket);
        return user;
    }
}
