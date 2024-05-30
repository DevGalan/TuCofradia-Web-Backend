package com.devgalan.tucofradia.services.server;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devgalan.tucofradia.models.Server;

@Service
public class ServerServiceImpl implements ServerService {

    @Override
    public List<Server> getRandomServers(Integer limit) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRandomServers'");
    }

    @Override
    public List<Server> getRandomPublicServers(Integer limit) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRandomPublicServers'");
    }

    @Override
    public Optional<Server> getServerById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getServerById'");
    }

    @Override
    public List<Server> getServersByName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getServersByName'");
    }

    @Override
    public Server enterServer(String code, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enterServer'");
    }

    @Override
    public Optional<Server> login(String email, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    @Override
    public Optional<Server> getServerByCode(String code) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getServerByCode'");
    }

    @Override
    public Boolean existsByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existsByEmail'");
    }

    @Override
    public Boolean existsById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existsById'");
    }

    @Override
    public Server saveServer(Server serverDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveServer'");
    }

    @Override
    public void deleteServer(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteServer'");
    }

}
