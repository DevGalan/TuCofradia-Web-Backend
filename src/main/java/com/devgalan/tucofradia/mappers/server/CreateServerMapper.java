package com.devgalan.tucofradia.mappers.server;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devgalan.tucofradia.dtos.server.CreateServerDto;
import com.devgalan.tucofradia.models.Server;
import com.devgalan.tucofradia.models.User;
import com.devgalan.tucofradia.repositories.UserRepository;

@Component
public class CreateServerMapper {

    @Autowired
    private UserRepository userRepository;

    public Server toEntity(CreateServerDto createServerDto) {
        Optional<User> user = userRepository.findById(createServerDto.getAdminId());
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        Server server = new Server();
        server.setName(createServerDto.getName());
        server.setCode(createServerDto.getCode());
        server.setDescription(createServerDto.getDescription());
        server.setPassword(createServerDto.getPassword());
        server.setAdmin(user.get());
        return server;
    }

    public List<Server> toEntity(List<CreateServerDto> createServerDtos) {
        return createServerDtos.stream().map(this::toEntity).toList();
    }
}
