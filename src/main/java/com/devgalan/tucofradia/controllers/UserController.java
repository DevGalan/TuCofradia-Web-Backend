package com.devgalan.tucofradia.controllers;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

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

import com.devgalan.tucofradia.dtos.LoginUserDto;
import com.devgalan.tucofradia.dtos.NoPasswordUserDto;
import com.devgalan.tucofradia.mappers.NoPasswordUserMapper;
import com.devgalan.tucofradia.models.User;
import com.devgalan.tucofradia.services.user.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    
    private final NoPasswordUserMapper noPasswordUserMapper;

    public UserController(UserService userService, NoPasswordUserMapper noPasswordUserMapper) {
        this.userService = userService;
        this.noPasswordUserMapper = noPasswordUserMapper;
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

    @GetMapping("login")
    public ResponseEntity<NoPasswordUserDto> login(@RequestBody LoginUserDto loginUserDto) {

        Optional<User> user = userService.login(loginUserDto.getEmail(), loginUserDto.getPassword());

        if (user.isPresent()) {
            return ResponseEntity.ok(noPasswordUserMapper.toDto(user.get()));
        } 
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("register")
    public ResponseEntity<NoPasswordUserDto> register(@RequestBody User user) {
        
        if (userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().build();
        }

        if (user.getId() != null && userService.existsById(user.getId())) {
            return ResponseEntity.badRequest().build();
        }

        user.setRegisterDate(new Date(System.currentTimeMillis()));
        user.setLastLogin(new Date(System.currentTimeMillis()));

        User createdUser = userService.saveUser(user);

        return ResponseEntity.ok(noPasswordUserMapper.toDto(createdUser));
    }
    
    @PutMapping("{id}")
    public ResponseEntity<NoPasswordUserDto> updateUser(@PathVariable Long id, @RequestBody User user) {

        Optional<User> dataBaseUser = userService.getUserById(id);

        if (dataBaseUser.isPresent()) {

            dataBaseUser.get().setUsername(user.getUsername());
            dataBaseUser.get().setProfileMessage(user.getProfileMessage());
            dataBaseUser.get().setProfilePicture(user.getProfilePicture());
            dataBaseUser.get().setPassword(user.getPassword());

            dataBaseUser.get().setLastLogin(new Date(System.currentTimeMillis()));

            User updatedUser = userService.saveUser(dataBaseUser.get());

            return ResponseEntity.ok(noPasswordUserMapper.toDto(updatedUser));
        } 
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        if (userService.existsById(id)) {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted");
        } 
        else {
            return ResponseEntity.notFound().build();
        }
    }

}
