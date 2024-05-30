package com.devgalan.tucofradia.dtos.news;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewsDto {

    private String title;
    
    private String body;

    private Date date;

}
