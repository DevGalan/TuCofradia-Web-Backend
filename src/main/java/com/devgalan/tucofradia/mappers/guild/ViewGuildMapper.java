package com.devgalan.tucofradia.mappers.guild;

import java.util.List;

import org.springframework.stereotype.Component;

import com.devgalan.tucofradia.dtos.guild.ViewGuildDto;
import com.devgalan.tucofradia.mappers.user.NoPasswordUserMapper;
import com.devgalan.tucofradia.models.Guild;
import com.devgalan.tucofradia.models.User;

@Component
public class ViewGuildMapper {

    private final NoPasswordUserMapper noPasswordUserMapper;

    public ViewGuildMapper(NoPasswordUserMapper noPasswordUserMapper) {
        this.noPasswordUserMapper = noPasswordUserMapper;
    }

    public ViewGuildDto toDto(Guild guild, User user) {
        return new ViewGuildDto(guild.getId(), guild.getName(), guild.getDescription(), guild.getReputation(),
                guild.getMoney(), guild.getBrothers(), guild.getFee(), noPasswordUserMapper.toDto(user));
    }

}
