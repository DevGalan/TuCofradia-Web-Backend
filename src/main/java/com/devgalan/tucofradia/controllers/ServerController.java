package com.devgalan.tucofradia.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devgalan.tucofradia.dtos.server.CreateServerDto;
import com.devgalan.tucofradia.dtos.server.JoinServerDto;
import com.devgalan.tucofradia.mappers.server.CreateServerMapper;
import com.devgalan.tucofradia.models.Server;
import com.devgalan.tucofradia.models.Survey;
import com.devgalan.tucofradia.services.server.ServerService;
import com.devgalan.tucofradia.services.survey.SurveyService;

@RestController
@RequestMapping("api/servers")
public class ServerController {

    private final ServerService serverService;

    private final SurveyService surveyService;

    private final CreateServerMapper createServerMapper;

    public ServerController(ServerService serverService, SurveyService surveyService,
            CreateServerMapper createServerMapper) {
        this.serverService = serverService;
        this.surveyService = surveyService;
        this.createServerMapper = createServerMapper;
    }

    @GetMapping("random")
    public List<Server> getRandomServers(@RequestParam(required = false, defaultValue = "15") Integer limit) {

        limit = fixLimitIfNeeded(limit);

        List<Server> randomServers = serverService.getRandomServers(limit);

        return randomServers;
    }

    @GetMapping("random/public")
    public List<Server> getRandomPublicServers(@RequestParam(required = false, defaultValue = "15") Integer limit) {

        limit = fixLimitIfNeeded(limit);

        List<Server> randomPublicServers = serverService.getRandomPublicServers(limit);

        return randomPublicServers;
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
    public ResponseEntity<Server> getServerById(@PathVariable Long id) {

        Optional<Server> server = serverService.getServerById(id);

        if (server.isPresent()) {
            return ResponseEntity.ok(server.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("search/{name}")
    public List<Server> searchServersByName(@PathVariable String name) {

        return serverService.getServersByName(name);
    }

    @GetMapping("search/{code}")
    public Optional<Server> searchServerByCode(@PathVariable String code) {

        return serverService.getServerByCode(code);
    }

    @GetMapping("join")
    public Server enterServer(@RequestBody JoinServerDto joinServerDto) {

        return serverService.enterServer(joinServerDto.getCode(), joinServerDto.getPassword());
    }

    @PostMapping("create")
    public Server createServer(@RequestBody CreateServerDto createServerDto) {

        return serverService.saveServer(createServerMapper.toEntity(createServerDto));
    }

}
