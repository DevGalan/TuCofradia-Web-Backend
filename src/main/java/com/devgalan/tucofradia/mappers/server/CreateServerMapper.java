package com.devgalan.tucofradia.mappers.server;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.devgalan.tucofradia.dtos.server.CreateServerDto;
import com.devgalan.tucofradia.models.Server;
import com.devgalan.tucofradia.models.User;
import com.devgalan.tucofradia.repositories.UserRepository;

@Component
public class CreateServerMapper {

    private final UserRepository userRepository;

    public CreateServerMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Server toEntity(CreateServerDto createServerDto) {
        Optional<User> user = userRepository.findById(createServerDto.getAdminId());
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        Server server = new Server();
        server.setName(createServerDto.getName());
        server.setDescription(createServerDto.getDescription());
        server.setPassword(createServerDto.getPassword());
        server.setMaxPlayers(createServerDto.getMaxPlayers());
        server.setAmountPlayers((byte) 0);
        server.setGameMonth((byte) Calendar.getInstance().get(Calendar.MONTH));
        server.setAdmin(user.get());
        return server;
    }

    public List<Server> toEntity(List<CreateServerDto> createServerDtos) {
        return createServerDtos.stream().map(this::toEntity).toList();
    }
}
