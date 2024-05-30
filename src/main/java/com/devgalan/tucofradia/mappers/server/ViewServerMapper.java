package com.devgalan.tucofradia.mappers.server;

import java.util.List;

import org.springframework.stereotype.Component;

import com.devgalan.tucofradia.dtos.server.ViewServerDto;
import com.devgalan.tucofradia.mappers.user.NoPasswordUserMapper;
import com.devgalan.tucofradia.models.Server;

@Component
public class ViewServerMapper {

    private final NoPasswordUserMapper noPasswordUserMapper;

    public ViewServerMapper(NoPasswordUserMapper noPasswordUserMapper) {
        this.noPasswordUserMapper = noPasswordUserMapper;
    }

    public ViewServerDto toDto(Server server) {
        return new ViewServerDto(server.getId(), server.getName(), server.getCode(), server.getDescription(),
        server.getGameMonth(), noPasswordUserMapper.toDto(server.getAdmin()));
    }

    public List<ViewServerDto> toDto(List<Server> servers) {
        return servers.stream().map(this::toDto).toList();
    }
}
