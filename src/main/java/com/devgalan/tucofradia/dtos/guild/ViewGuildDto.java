package com.devgalan.tucofradia.dtos.guild;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewGuildDto {

    private Long id;

    private String name;

    private String description;

    private Integer reputation;

    private Long money;

    private Integer brothers;

    private Byte fee;

}
