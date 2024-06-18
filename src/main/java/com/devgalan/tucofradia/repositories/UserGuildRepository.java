package com.devgalan.tucofradia.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devgalan.tucofradia.models.UserGuild;

@Repository
public interface UserGuildRepository extends JpaRepository<UserGuild, Long> {

    List<UserGuild> findByUserId(Long userId);

    @Query(nativeQuery = true, value = "SELECT ug.id, ug.user_id, ug.guild_id FROM user_guilds as ug INNER JOIN guilds as g ON ug.guild_id = g.id WHERE ug.user_id = :userId AND g.server_id = :serverId")
    Optional<UserGuild> findByUserIdAndGuildServerId(Long userId, Long serverId);

    @Query(nativeQuery = true, value = "SELECT ug.id, ug.user_id, ug.guild_id FROM user_guilds as ug INNER JOIN guilds as g ON ug.guild_id = g.id WHERE g.server_id = :serverId")
    Optional<UserGuild> findByServerId(Long serverId);

}
