package com.devgalan.tucofradia.mappers.guild;

import java.util.List;

import org.springframework.stereotype.Component;

import com.devgalan.tucofradia.dtos.guild.ViewGuildDto;
import com.devgalan.tucofradia.models.Guild;

@Component
public class ViewGuildMapper {

    public ViewGuildDto toDto(Guild guild) {
        return new ViewGuildDto(guild.getId(), guild.getName(), guild.getDescription(), guild.getReputation(),
                guild.getMoney(), guild.getBrothers(), guild.getFee());
    }

    public List<ViewGuildDto> toDto(List<Guild> guilds) {
        return guilds.stream().map(this::toDto).toList();
    }

}
