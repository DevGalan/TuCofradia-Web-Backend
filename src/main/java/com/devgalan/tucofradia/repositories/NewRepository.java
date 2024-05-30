package com.devgalan.tucofradia.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devgalan.tucofradia.models.News;

@Repository
public interface NewRepository extends JpaRepository<News, Long> {

    List<News> findAll();

}
