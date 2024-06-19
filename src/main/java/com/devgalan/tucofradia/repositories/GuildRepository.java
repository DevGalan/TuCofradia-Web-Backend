package com.devgalan.tucofradia.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devgalan.tucofradia.models.Guild;

@Repository
public interface GuildRepository extends JpaRepository<Guild, Long> {

    List<Guild> findByServerId(Long serverId);

}
