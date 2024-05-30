package com.devgalan.tucofradia.services.news;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devgalan.tucofradia.models.News;
import com.devgalan.tucofradia.repositories.NewRepository;

@Service
public class NewServiceImpl implements NewService {

    private final NewRepository newRepository;

    public NewServiceImpl(NewRepository newRepository) {
        this.newRepository = newRepository;
    }

    @Override
    public List<News> getNews() {
        return newRepository.findAll();
    }

    @Override
    public void createNew(News createNew) {
        newRepository.save(createNew);
    }

    @Override
    public void deleteNew(Long id) {
        newRepository.deleteById(id);
    }

    @Override
    public News updateNew(News updateNew) {
        return newRepository.save(updateNew);
    }

    @Override
    public Optional<News> getNewById(Long id) {
        return newRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return newRepository.existsById(id);
    }

}
