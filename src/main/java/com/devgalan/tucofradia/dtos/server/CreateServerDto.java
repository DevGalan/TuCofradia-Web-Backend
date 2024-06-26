package com.devgalan.tucofradia.dtos.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateServerDto {

    private String name;

    private String description;

    private String password;

    private Byte maxPlayers;

    private Long adminId;
}
