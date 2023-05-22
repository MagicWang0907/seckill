package com.wang.seckill.exception;

import com.wang.seckill.vo.ResponseBean;
import com.wang.seckill.vo.ResponseEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseBean exceptionHandler(Exception e){
        if(e instanceof GlobalException){
            GlobalException ge = (GlobalException) e;
            return ResponseBean.error(ge.getResponseEnum());
        }else if(e instanceof BindException){
            BindException be = (BindException) e;
            ResponseBean error = ResponseBean.error(ResponseEnum.BIND_ERROR);
            error.setMessage("参数校验异常:"+be.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return error;
        }
        return  ResponseBean.error(ResponseEnum.ERROR);
    }
}
