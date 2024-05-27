package com.devgalan.tucofradia.mappers.user;

import java.util.List;

import org.springframework.stereotype.Component;

import com.devgalan.tucofradia.dtos.user.RegisterUserDto;
import com.devgalan.tucofradia.models.User;

@Component
public class RegisterUserMapper {

    public User toEntity(RegisterUserDto registerUser) {
        User user = new User();
        user.setUsername(registerUser.getUsername());
        user.setEmail(registerUser.getEmail());
        user.setPassword(registerUser.getPassword());
        return user;
    }

    public List<User> toEntity(List<RegisterUserDto> registerUsers) {
        return registerUsers.stream().map(this::toEntity).toList();
    }

}
