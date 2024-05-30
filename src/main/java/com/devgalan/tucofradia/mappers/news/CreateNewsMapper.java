package com.devgalan.tucofradia.mappers.news;

import java.util.List;

import org.springframework.stereotype.Component;

import com.devgalan.tucofradia.dtos.news.CreateNewsDto;
import com.devgalan.tucofradia.models.News;

@Component
public class CreateNewsMapper {

    public News toEntity(CreateNewsDto createNewsDto) {
        News news = new News();
        news.setTitle(createNewsDto.getTitle());
        news.setBody(createNewsDto.getBody());
        news.setDate(createNewsDto.getDate());
        return news;
    }

    public List<News> toEntity(List<CreateNewsDto> createNewsDtos) {
        return createNewsDtos.stream().map(this::toEntity).toList();
    }

}
