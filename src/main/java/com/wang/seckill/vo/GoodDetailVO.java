package com.wang.seckill.vo;

import com.wang.seckill.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodDetailVO {
    private User user;

    private GoodsVO goods;

    private int remainSeconds;

    private int secKillStatus;
}
