package com.devgalan.tucofradia.services.news;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devgalan.tucofradia.models.New;
import com.devgalan.tucofradia.repositories.NewRepository;

@Service
public class NewServiceImpl implements NewService {

    private final NewRepository newRepository;

    public NewServiceImpl(NewRepository newRepository) {
        this.newRepository = newRepository;
    }

    @Override
    public List<New> getNews() {
        return newRepository.findAll();
    }

    @Override
    public void createNew(New createNew) {
        newRepository.save(createNew);
    }

    @Override
    public void deleteNew(Long id) {
        newRepository.deleteById(id);
    }

    @Override
    public New updateNew(New updateNew) {
        return newRepository.save(updateNew);
    }

    @Override
    public Optional<New> getNewById(Long id) {
        return newRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return newRepository.existsById(id);
    }

}
