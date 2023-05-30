package com.wang.seckill.entity;


import com.wang.seckill.vo.GoodsVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecKillMessage {
    private User user;

    private GoodsVO goodsVO;
}
