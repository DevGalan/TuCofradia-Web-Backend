package com.devgalan.tucofradia.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devgalan.tucofradia.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query(nativeQuery = true, value = "SELECT * FROM users ORDER BY RAND() LIMIT :limit")
    List<User> getRandomUsers(Integer limit);

    List<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

}
