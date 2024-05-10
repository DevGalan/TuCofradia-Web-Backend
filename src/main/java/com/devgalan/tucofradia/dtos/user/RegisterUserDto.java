package com.devgalan.tucofradia.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {

    private String username;

    private String email;

    private String password;

    private String profileMessage;

}
