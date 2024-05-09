package com.devgalan.tucofradia.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.devgalan.tucofradia.dtos.NoPasswordUserDto;
import com.devgalan.tucofradia.models.User;

@Component
public class NoPasswordUserMapper {

    public NoPasswordUserDto toDto(User user) {
        return new NoPasswordUserDto(user.getId(), user.getUsername(), user.getEmail(), 
                                    user.getProfileMessage(), user.getLastLogin(), user.getRegisterDate(), 
                                    user.getProfilePicture());
    }

    public List<NoPasswordUserDto> toDto(List<User> users) {
        return users.stream().map(this::toDto).toList();
    }

}
