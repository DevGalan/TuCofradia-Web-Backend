package com.devgalan.tucofradia.dtos.user;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoPasswordUserDto {

    private Long id;

    private String username;

    private String email;

    private String profileMessage;

    private Date lastLogin;

    private Date registerDate;

    private String profilePicture;

}
