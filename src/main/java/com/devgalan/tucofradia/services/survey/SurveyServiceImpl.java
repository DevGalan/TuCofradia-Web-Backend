package com.devgalan.tucofradia.services.survey;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devgalan.tucofradia.models.Survey;

@Service
public class SurveyServiceImpl implements SurveyService {

    @Override
    public List<Survey> getRandomSurveys(Integer limit) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRandomSurveys'");
    }

    @Override
    public Optional<Survey> getSurveyById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSurveyById'");
    }

    @Override
    public List<Survey> getSurveysByUsername(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSurveysByUsername'");
    }

    @Override
    public Optional<Survey> login(String email, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    @Override
    public Boolean existsByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existsByEmail'");
    }

    @Override
    public Boolean existsById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existsById'");
    }

    @Override
    public Survey saveSurvey(Survey userDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveSurvey'");
    }

    @Override
    public void deleteSurvey(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteSurvey'");
    }
}
