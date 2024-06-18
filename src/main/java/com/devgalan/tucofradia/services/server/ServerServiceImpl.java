package com.devgalan.tucofradia.services.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devgalan.tucofradia.models.Server;
import com.devgalan.tucofradia.models.UserGuild;
import com.devgalan.tucofradia.repositories.ServerRepository;
import com.devgalan.tucofradia.repositories.UserGuildRepository;

@Service
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;

    private final UserGuildRepository userGuildRepository;

    public ServerServiceImpl(ServerRepository serverRepository, UserGuildRepository userGuildRepository) {
        this.serverRepository = serverRepository;
        this.userGuildRepository = userGuildRepository;
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

    @Override
    public String generateCode() {
        String code;
        do {
            code = generateRandomCode();
        } while (serverRepository.existsByCode(code));
        return code;
    }

    private String generateRandomCode() {
        String code = "";
        for (int i = 0; i < 6; i++) {
            code += (char) (Math.random() * 26 + 65);
        }
        return code;
    }

    @Override
    public List<Server> getServersByUserId(Long adminId) {

        return serverRepository.findByAdminId(adminId);

    }

    @Override
    public List<Server> getJoinedServersByUserId(Long userId) {
        var userGuilds = userGuildRepository.findByUserId(userId);
        
        List<Server> servers = new ArrayList<>();
        for (UserGuild userGuild : userGuilds) {
            servers.add(userGuild.getGuild().getServer());
        }

        return servers;
    }

    @Override
    public void leaveServer(Long serverId, Long userId) {
        var server = serverRepository.findById(serverId);
        if (server.isEmpty()) {
            return;
        }
        
        var userGuild = userGuildRepository.findByUserIdAndGuildServerId(userId, serverId);
        if (!userGuild.isEmpty()){
            userGuildRepository.delete(userGuild.get());
            server.get().setAmountPlayers((byte) (server.get().getAmountPlayers() - 1));
        }
        
        if (server.get().getAmountPlayers() <= 0) {
            serverRepository.delete(server.get());
        } else {
            if (server.get().getAdmin().getId().equals(userId)) {
                var newAdmin = userGuildRepository.findByServerId(serverId);
                if (!newAdmin.isEmpty()) {
                    server.get().setAdmin(newAdmin.get().getUser());
                } else {
                    server.get().setAdmin(null);
                }
            }
            serverRepository.save(server.get());
        }
    }

}
