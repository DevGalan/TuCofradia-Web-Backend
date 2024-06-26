package com.devgalan.tucofradia.services.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devgalan.tucofradia.dtos.guild.CreateGuildDto;
import com.devgalan.tucofradia.dtos.guild.ViewGuildDto;
import com.devgalan.tucofradia.mappers.guild.CreateGuildMapper;
import com.devgalan.tucofradia.mappers.guild.ViewGuildMapper;
import com.devgalan.tucofradia.models.Guild;
import com.devgalan.tucofradia.models.Server;
import com.devgalan.tucofradia.models.UserGuild;
import com.devgalan.tucofradia.repositories.GuildRepository;
import com.devgalan.tucofradia.repositories.ServerRepository;
import com.devgalan.tucofradia.repositories.UserGuildRepository;
import com.devgalan.tucofradia.repositories.UserRepository;

@Service
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;

    private final UserGuildRepository userGuildRepository;

    private final UserRepository userRepository;

    private final GuildRepository guildRepository;

    private final CreateGuildMapper createGuildMapper;

    private final ViewGuildMapper viewGuildMapper;

    public ServerServiceImpl(ServerRepository serverRepository, UserGuildRepository userGuildRepository,
            UserRepository userRepository, GuildRepository guildRepository, CreateGuildMapper createGuildMapper, ViewGuildMapper viewGuildMapper) {
        this.serverRepository = serverRepository;
        this.userGuildRepository = userGuildRepository;
        this.userRepository = userRepository;
        this.guildRepository = guildRepository;
        this.createGuildMapper = createGuildMapper;
        this.viewGuildMapper = viewGuildMapper;
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

        var userGuilds = userGuildRepository.findByUserId(userId);
        var userGuild = userGuilds.stream().filter(ug -> ug.getGuild().getServer().getId().equals(serverId)).findFirst();
        
        if (!userGuild.isEmpty()) {
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

    @Override
    public Guild createGuild(Long serverId, Long userId, CreateGuildDto createGuildDto) {
        var server = serverRepository.findById(serverId);
        if (server.isEmpty()) {
            System.out.println("Server not found");
            return null;
        }
        var user = userRepository.findById(userId);
        if (user.isEmpty()) {
            System.out.println("User not found");
            return null;
        }

        var guild = createGuildMapper.toEntity(createGuildDto);
        guild.setServer(server.get());

        var userGuild = new UserGuild();
        userGuild.setGuild(guild);
        userGuild.setUser(user.get());
        userGuildRepository.save(userGuild).getId();
        server.get().setAmountPlayers((byte) (server.get().getAmountPlayers() + 1));
        serverRepository.save(server.get());
        return userGuild.getGuild();
    }

    @Override
    public List<ViewGuildDto> getGuilds(Long serverId) {
        var guilds = guildRepository.findByServerId(serverId);
        var viewGuilds = new ArrayList<ViewGuildDto>();
        for (int i = 0; i < guilds.size(); i++) {
            var userGuild = userGuildRepository.findByGuildId(guilds.get(i).getId());
            if (userGuild.isEmpty()) {
                continue;
            }
            viewGuilds.add(viewGuildMapper.toDto(guilds.get(i), userGuild.get().getUser()));
        }
        return viewGuilds;
    }

}
