package com.devgalan.tucofradia.services.server;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devgalan.tucofradia.models.Server;
import com.devgalan.tucofradia.repositories.ServerRepository;

@Service
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;

    public ServerServiceImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public List<Server> getRandomServers(Integer limit) {
        return serverRepository.getRandomServers(limit);
    }

    @Override
    public List<Server> getRandomPublicServers(Integer limit) {
        return serverRepository.getRandomPublicServers(limit);
    }

    @Override
    public Optional<Server> getServerById(Long id) {
        return serverRepository.findById(id);
    }

    @Override
    public List<Server> getServersByName(String name) {
        return serverRepository.findByName(name);
    }

    @Override
    public Optional<Server> enterServer(String code, String password) {
        return serverRepository.findByCodeAndPassword(code, password);
    }

    @Override
    public Optional<Server> getServerByCode(String code) {
        return serverRepository.findByCode(code);
    }

    @Override
    public Boolean existsById(Long id) {
        return serverRepository.existsById(id);
    }

    @Override
    public Server saveServer(Server serverDto) {
        return serverRepository.save(serverDto);
    }

    @Override
    public void deleteServer(Long id) {
        serverRepository.deleteById(id);
    }

    @Override
    public List<Server> getAllServers() {
        return serverRepository.findAll();
    }

}
