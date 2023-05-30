package com.wang.seckill.dto;

/*
    前端功能尚未实现
    待扩张
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserDTO {
    String name;

    Long mobile;

    String address;
}
