package com.devgalan.tucofradia.mappers.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateServerDto {

    private String description;

    private String password;

}
