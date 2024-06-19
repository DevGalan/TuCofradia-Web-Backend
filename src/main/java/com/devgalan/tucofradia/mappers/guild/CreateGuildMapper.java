package com.devgalan.tucofradia.mappers.guild;

import java.util.List;

import org.springframework.stereotype.Component;

import com.devgalan.tucofradia.dtos.guild.CreateGuildDto;
import com.devgalan.tucofradia.models.Guild;

@Component
public class CreateGuildMapper {

    public Guild toEntity(CreateGuildDto createGuildDto) {
        Guild guild = new Guild();
        guild.setName(createGuildDto.getName());
        guild.setFee(createGuildDto.getFee());
        guild.setBrothers(0);
        guild.setDescription("");
        guild.setMoney(15000L);
        guild.setReputation(0);
        return guild;
    }

    public List<Guild> toEntity(List<CreateGuildDto> createGuildDtos) {
        return createGuildDtos.stream().map(this::toEntity).toList();
    }

}
