package com.devgalan.tucofradia.mappers.news;

import java.util.List;

import org.springframework.stereotype.Component;

import com.devgalan.tucofradia.dtos.news.ViewNewsDto;
import com.devgalan.tucofradia.models.News;

@Component
public class ViewNewsMapper {

    public ViewNewsDto toDto(News news) {
        return new ViewNewsDto(news.getId(), news.getTitle(), news.getBody(), news.getDate(),
                news.getImage() != null ? news.getImage().getOnlinePath() : null);
    }

    public List<ViewNewsDto> toDto(List<News> news) {
        return news.stream().map(this::toDto).toList();
    }

}
