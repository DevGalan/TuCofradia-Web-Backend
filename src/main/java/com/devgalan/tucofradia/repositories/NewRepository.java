package com.devgalan.tucofradia.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devgalan.tucofradia.models.New;

@Repository
public interface NewRepository extends JpaRepository<New, Long> {

    List<New> findAll();

}
