package com.devgalan.tucofradia.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devgalan.tucofradia.models.Guild;

@Repository
public interface GuildRepository extends JpaRepository<Guild, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM guilds WHERE server_id = :serverId")
    List<Guild> findByServerId(Long serverId);

}
