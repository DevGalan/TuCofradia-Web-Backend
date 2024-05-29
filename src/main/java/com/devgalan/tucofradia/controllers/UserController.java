package com.devgalan.tucofradia.controllers;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.devgalan.tucofradia.dtos.user.LoginUserDto;
import com.devgalan.tucofradia.dtos.user.NoPasswordUserDto;
import com.devgalan.tucofradia.dtos.user.RegisterUserDto;
import com.devgalan.tucofradia.dtos.user.UpdateUserDto;
import com.devgalan.tucofradia.mappers.user.NoPasswordUserMapper;
import com.devgalan.tucofradia.mappers.user.RegisterUserMapper;
import com.devgalan.tucofradia.models.User;
import com.devgalan.tucofradia.services.user.UserService;
import com.devgalan.tucofradia.tool.ResponseWithMessage;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;
    
    private final NoPasswordUserMapper noPasswordUserMapper;

    private final RegisterUserMapper registerUserMapper;

    public UserController(UserService userService, NoPasswordUserMapper noPasswordUserMapper, 
                            RegisterUserMapper registerUserMapper) {
        this.userService = userService;
        this.noPasswordUserMapper = noPasswordUserMapper;
        this.registerUserMapper = registerUserMapper;
    }

    @GetMapping("random")
    public List<NoPasswordUserDto> getRandomUsers(@RequestParam(required = false, defaultValue = "15") Integer limit) {

        if (limit < 1) {
            limit = 1;
        } 
        else if (limit > 30) {
            limit = 30;
        }

        List<NoPasswordUserDto> randomUsers = noPasswordUserMapper.toDto(userService.getRandomUsers(limit));

        return randomUsers;
    }

    @GetMapping("{id}")
    public ResponseEntity<NoPasswordUserDto> getUserById(@PathVariable Long id) {

        Optional<User> user = userService.getUserById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(noPasswordUserMapper.toDto(user.get()));
        } 
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("search/{username}")
    public List<NoPasswordUserDto> getUsersByUsername(@PathVariable String username) {

        List<NoPasswordUserDto> searchedUsers = noPasswordUserMapper.toDto(userService.getUsersByUsername(username));

        return searchedUsers;
    }

    @PostMapping("login")
    public ResponseEntity<NoPasswordUserDto> login(@RequestBody LoginUserDto loginUserDto) {

        Optional<User> user = userService.login(loginUserDto.getEmail().toLowerCase().trim(), loginUserDto.getPassword());

        user.ifPresent(u -> { 
            u.setLastLogin(new Date(System.currentTimeMillis()));
            userService.updateUser(u);
        });

        if (user.isPresent()) {
            return ResponseEntity.ok(noPasswordUserMapper.toDto(user.get()));
        } 
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("register")
    public ResponseEntity<ResponseWithMessage<NoPasswordUserDto>> register(@RequestBody RegisterUserDto registerUser) {

        registerUser.setEmail(registerUser.getEmail().toLowerCase().trim());

        if (!isValidEmail(registerUser.getEmail())) {
            return ResponseEntity.badRequest().body(new ResponseWithMessage<NoPasswordUserDto>("Correo electrónico inválido"));
        }

        if (registerUser.getPassword().length() < 8) {
            return ResponseEntity.badRequest().body(new ResponseWithMessage<NoPasswordUserDto>("La contraseña debe tener al menos 8 caracteres"));
        }
        
        if (userService.existsByEmail(registerUser.getEmail())) {
            return ResponseEntity.badRequest().body(new ResponseWithMessage<NoPasswordUserDto>("El correo electrónico ya está en uso"));
        }

        User user = registerUserMapper.toEntity(registerUser);

        user.setRegisterDate(new Date(System.currentTimeMillis()));
        user.setLastLogin(new Date(System.currentTimeMillis()));

        return ResponseEntity.ok(new ResponseWithMessage<NoPasswordUserDto>("Usuario registrado", noPasswordUserMapper.toDto(userService.saveUser(user))));
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    @PutMapping("{id}")
    public ResponseEntity<NoPasswordUserDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto user) {

        Optional<User> dataBaseUser = userService.getUserById(id);

        if (dataBaseUser.isPresent()) {

            if (user.getUsername() == null && user.getProfileMessage() == null) {
                return ResponseEntity.badRequest().build();
            }

            if (user.getUsername() != null) dataBaseUser.get().setUsername(user.getUsername());
            if (user.getProfileMessage() != null) dataBaseUser.get().setProfileMessage(user.getProfileMessage());

            User updatedUser = userService.updateUser(dataBaseUser.get());

            return ResponseEntity.ok(noPasswordUserMapper.toDto(updatedUser));
        } 
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        if (userService.existsById(id)) {
            // TODO
            // Eliminar Guilds, pueden ser dadas a otro user
            // Dar admin del server a un random, si no hay mas users, eliminar server, puede ser dado a otro user
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted");
        } 
        else {
            return ResponseEntity.notFound().build();
        }
    }

}
