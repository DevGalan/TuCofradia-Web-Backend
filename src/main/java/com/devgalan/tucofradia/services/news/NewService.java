package com.devgalan.tucofradia.services.news;

import java.util.List;
import java.util.Optional;

import com.devgalan.tucofradia.models.New;

public interface NewService {

    List<New> getNews();

    void createNew(New createNew);

    void deleteNew(Long id);

    New updateNew(New updateNew);

    Optional<New> getNewById(Long id);

    boolean existsById(Long id);

}
