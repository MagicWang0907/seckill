package com.wang.seckill.vo;

import com.wang.seckill.dto.OrderDTO;
import com.wang.seckill.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailVO {
    GoodsVO goods;
    OrderDTO order;
    UserDTO user;
}
