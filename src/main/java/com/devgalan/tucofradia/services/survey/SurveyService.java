package com.devgalan.tucofradia.services.survey;

import java.util.List;
import java.util.Optional;

import com.devgalan.tucofradia.models.Survey;

public interface SurveyService {

    List<Survey> getRandomSurveys(Integer limit);

    Optional<Survey> getSurveyById(Long id);

    List<Survey> getSurveysByUsername(String username);

    Optional<Survey> login(String email, String password);

    Boolean existsByEmail(String email);

    Boolean existsById(Long id);

    Survey saveSurvey(Survey userDto);

    void deleteSurvey(Long id);

}
