package com.devgalan.tucofradia.services.server;

import java.util.List;
import java.util.Optional;

import com.devgalan.tucofradia.models.Server;

public interface ServerService {

    List<Server> getRandomServers(Integer limit);

    List<Server> getRandomPublicServers(Integer limit);

    Optional<Server> getServerById(Long id);

    List<Server> getServersByName(String name);

    Server enterServer(String code, String password);

    Optional<Server> login(String email, String password);

    Optional<Server> getServerByCode(String code);

    Boolean existsByEmail(String email);

    Boolean existsById(Long id);

    Server saveServer(Server serverDto);

    void deleteServer(Long id);

}
