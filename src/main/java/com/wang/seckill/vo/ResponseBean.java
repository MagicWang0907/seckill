package com.wang.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBean {
    private int code;
    private String message;
    private Object object;

    public static ResponseBean success(){
        return new ResponseBean(ResponseEnum.SUCCESS.getCode(),ResponseEnum.SUCCESS.getMessage(),null);
    }

    public static ResponseBean success(Object object){
        return new ResponseBean(ResponseEnum.SUCCESS.getCode(),ResponseEnum.SUCCESS.getMessage(),object);
    }

    public static ResponseBean error(ResponseEnum responseEnum){
        return new ResponseBean(responseEnum.getCode(),responseEnum.getMessage(),null);
    }

}
