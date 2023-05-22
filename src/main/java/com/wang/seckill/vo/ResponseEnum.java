package com.wang.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public enum ResponseEnum {
    SUCCESS(200,"SUCESS"),
    LOGIN_ERROR(500210,"用户名或密码错误"),
    ERROR(500,"服务端异常"),
    MOBILE_ERROR(500211,"手机格式错误"),
    BIND_ERROR(500212,"参数校验异常"),
    EMPTY_STCOK(500501,"库存不足"),
    REPEAT_ERROR(500502,"请勿重复操作");
    private final Integer code;
    private final String message;
}
