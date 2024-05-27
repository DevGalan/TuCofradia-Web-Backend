package com.devgalan.tucofradia.dtos.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinServerDto {

    private String code;

    private String password;

}
