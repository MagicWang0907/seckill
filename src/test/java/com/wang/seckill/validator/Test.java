package com.wang.seckill.validator;

import com.wang.seckill.vo.LoginVO;

public class Test {
    @org.junit.jupiter.api.Test
    public void test(){
        LoginVO loginVO = new LoginVO();
        loginVO.setMobile("211413421");
        System.out.println(loginVO);
    }
}
