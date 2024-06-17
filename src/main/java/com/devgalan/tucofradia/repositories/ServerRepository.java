package com.devgalan.tucofradia.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devgalan.tucofradia.models.Server;

@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM servers ORDER BY RAND() LIMIT :limit")
    List<Server> getRandomServers(Integer limit);

    @Query(nativeQuery = true, value = "SELECT * FROM servers WHERE password = '' ORDER BY RAND() LIMIT :limit")
    List<Server> getRandomPublicServers(Integer limit);

    Optional<Server> findByCodeAndPassword(String code, String password);

    List<Server> findByName(String name);

    Optional<Server> findByCode(String code);

}
