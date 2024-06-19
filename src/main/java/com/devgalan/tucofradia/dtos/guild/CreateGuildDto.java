package com.devgalan.tucofradia.dtos.guild;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGuildDto {
    private String name;
    private Byte fee;
}
