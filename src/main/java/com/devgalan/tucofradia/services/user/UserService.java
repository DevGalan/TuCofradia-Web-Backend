package com.devgalan.tucofradia.services.user;

import java.util.List;
import java.util.Optional;

import com.devgalan.tucofradia.models.User;

public interface UserService {

    List<User> getRandomUsers(Integer limit);

    Optional<User> getUserById(Long id);

    List<User> getUsersByUsername(String username);

    Optional<User> login(String email, String password);

    Boolean existsByEmail(String email);

    Boolean existsById(Long id);

    User saveUser(User userDto);

    void deleteUser(Long id);

}
