package com.project.capstone.domain.dto;

import com.project.capstone.domain.entity.News;
import com.project.capstone.domain.entity.Team;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NewsResponseDto {
    private Long newsId;
    private String image;
    private String headline;
    private String snippet;
    private String url;
    private LocalDateTime time;
    private Team team;

    public static NewsResponseDto of(News news) {
        return NewsResponseDto.builder()
                .newsId(news.getId())
                .image(news.getImage())
                .headline(news.getHeadline())
                .snippet(news.getSnippet())
                .url(news.getUrl())
                .time(news.getTime())
                .team(news.getTeam())
                .build();
    }
}
