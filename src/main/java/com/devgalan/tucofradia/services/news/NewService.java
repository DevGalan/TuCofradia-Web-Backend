package com.devgalan.tucofradia.services.news;

import java.util.List;
import java.util.Optional;

import com.devgalan.tucofradia.models.News;

public interface NewService {

    List<News> getNews();

    void createNew(News createNew);

    void deleteNew(Long id);

    News updateNew(News updateNew);

    Optional<News> getNewById(Long id);

    boolean existsById(Long id);

}
