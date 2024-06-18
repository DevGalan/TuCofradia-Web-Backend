package com.devgalan.tucofradia.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devgalan.tucofradia.dtos.server.CreateServerDto;
import com.devgalan.tucofradia.dtos.server.JoinServerDto;
import com.devgalan.tucofradia.dtos.server.ViewServerDto;
import com.devgalan.tucofradia.mappers.server.CreateServerMapper;
import com.devgalan.tucofradia.mappers.server.UpdateServerDto;
import com.devgalan.tucofradia.mappers.server.ViewServerMapper;
import com.devgalan.tucofradia.models.Server;
import com.devgalan.tucofradia.services.server.ServerService;

@RestController
@RequestMapping("api/servers")
public class ServerController {

    private final ServerService serverService;

    private final CreateServerMapper createServerMapper;

    private final ViewServerMapper viewServerMapper;

    public ServerController(ServerService serverService,
            CreateServerMapper createServerMapper, ViewServerMapper viewServerMapper) {
        this.serverService = serverService;
        this.createServerMapper = createServerMapper;
        this.viewServerMapper = viewServerMapper;
    }

    @GetMapping("")
    public List<ViewServerDto> getAllServers() {

        return viewServerMapper.toDto(serverService.getAllServers());
    }

    @GetMapping("random")
    public List<ViewServerDto> getRandomServers(@RequestParam(required = false, defaultValue = "15") Integer limit) {

        limit = fixLimitIfNeeded(limit);

        List<Server> randomServers = serverService.getRandomServers(limit);

        return viewServerMapper.toDto(randomServers);
    }

    @GetMapping("random/public")
    public List<ViewServerDto> getRandomPublicServers(
            @RequestParam(required = false, defaultValue = "15") Integer limit) {

        limit = fixLimitIfNeeded(limit);

        List<Server> randomPublicServers = serverService.getRandomPublicServers(limit);

        return viewServerMapper.toDto(randomPublicServers);
    }

    private int fixLimitIfNeeded(int limit) {
        if (limit < 1) {
            limit = 1;
        } else if (limit > 30) {
            limit = 30;
        }
        return limit;
    }

    @GetMapping("{id}")
    public ResponseEntity<ViewServerDto> getServerById(@PathVariable Long id) {

        Optional<Server> server = serverService.getServerById(id);

        if (server.isPresent()) {
            return ResponseEntity.ok(viewServerMapper.toDto(server.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}/update")
    public ResponseEntity<ViewServerDto> updateServer(@PathVariable Long id, @RequestBody UpdateServerDto updateServerDto) {

        Optional<Server> server = serverService.getServerById(id);

        if (server.isPresent()) {
            server.get().setDescription(updateServerDto.getDescription());
            server.get().setPassword(updateServerDto.getPassword());
            return ResponseEntity.ok(viewServerMapper.toDto(serverService.saveServer(server.get())));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("user/{userId}")
    public List<ViewServerDto> getServersByUserId(@PathVariable Long userId) {

        var ownServers = serverService.getServersByUserId(userId);

        var joinedServers = serverService.getJoinedServersByUserId(userId);

        Set<Server> allServers = new HashSet<>();
        allServers.addAll(ownServers);
        allServers.addAll(joinedServers);

        return allServers.stream().map(viewServerMapper::toDto).toList();
    }

    @GetMapping("search/{name}")
    public List<ViewServerDto> searchServersByName(@PathVariable String name) {

        return viewServerMapper.toDto(serverService.getServersByName(name));
    }

    @GetMapping("search/{code}")
    public ViewServerDto searchServerByCode(@PathVariable String code) {

        var server = serverService.getServerByCode(code);

        if (server.isPresent()) {
            return viewServerMapper.toDto(server.get());
        } else {
            return null;
        }
    }

    @PostMapping("join")
    public ViewServerDto enterServer(@RequestBody JoinServerDto joinServerDto) {

        if (joinServerDto.getPassword() == null) {
            joinServerDto.setPassword("");
        }

        var server = serverService.enterServer(joinServerDto.getCode(), joinServerDto.getPassword());

        if (server.isPresent() && server.get().getMaxPlayers() > server.get().getAmountPlayers()) {
            return viewServerMapper.toDto(server.get());
        }

        return null;
    }

    @PostMapping("create")
    public ViewServerDto createServer(@RequestBody CreateServerDto createServerDto) {

        String code = serverService.generateCode();

        Server server = createServerMapper.toEntity(createServerDto);
        server.setCode(code);

        return viewServerMapper.toDto(serverService.saveServer(server));
    }

    @DeleteMapping("{serverId}/leave/{userId}")
    public void leaveServer(@PathVariable Long serverId, @PathVariable Long userId) {
            
        serverService.leaveServer(serverId, userId);
    }

}
