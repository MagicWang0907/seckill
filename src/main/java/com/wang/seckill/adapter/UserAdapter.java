package com.wang.seckill.adapter;

import com.wang.seckill.dto.UserDTO;
import com.wang.seckill.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter {
    public UserDTO userToUserDTO(User user){
        return UserDTO.builder()
                //.address() 待完善
                .mobile(user.getId())
                .name(user.getNickname())
                .build();
    }
}
