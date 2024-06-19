package com.devgalan.tucofradia.services.server;

import java.util.List;
import java.util.Optional;

import com.devgalan.tucofradia.dtos.guild.CreateGuildDto;
import com.devgalan.tucofradia.models.Guild;
import com.devgalan.tucofradia.models.Server;

public interface ServerService {

    List<Server> getRandomServers(Integer limit);

    List<Server> getRandomPublicServers(Integer limit);

    Optional<Server> getServerById(Long id);

    List<Server> getServersByName(String name);

    Optional<Server> enterServer(String code, String password);

    Optional<Server> getServerByCode(String code);

    Boolean existsById(Long id);

    Server saveServer(Server serverDto);

    void deleteServer(Long id);

    List<Server> getAllServers();

    String generateCode();

    List<Server> getServersByUserId(Long userId);

    List<Server> getJoinedServersByUserId(Long userId);

    void leaveServer(Long serverId, Long userId);

    Guild createGuild(Long serverId, Long userId, CreateGuildDto createGuildDto);

    List<Guild> getGuilds(Long serverId);

}
