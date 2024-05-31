package com.devgalan.tucofradia.dtos.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateServerDto {

    private String name;

    private String code;

    private String description;

    private String password;

    private Byte maxGuilds;

    private Long adminId;
}
