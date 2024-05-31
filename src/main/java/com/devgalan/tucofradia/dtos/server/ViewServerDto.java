package com.devgalan.tucofradia.dtos.server;

import com.devgalan.tucofradia.dtos.user.NoPasswordUserDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewServerDto {

    private Long id;

    private String name;

    private String code;

    private String description;

    private Byte gameMonth;
    
    private Byte maxGuilds;

    private NoPasswordUserDto admin;
}
