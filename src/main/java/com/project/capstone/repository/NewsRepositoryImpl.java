package com.project.capstone.repository;

import com.project.capstone.domain.dto.NewsResponseDto;
import com.project.capstone.domain.entity.News;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.capstone.domain.entity.QNews.news;

@RequiredArgsConstructor
@Repository
public class NewsRepositoryImpl implements NewsRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<NewsResponseDto> searchAllNews(Pageable pageable) {
        List<News> content = queryFactory
                .selectFrom(news)
                .orderBy(news.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<NewsResponseDto> pages = content
                .stream()
                .map(NewsResponseDto::of)
                .collect(Collectors.toList());

        int totalSize = queryFactory
                .selectFrom(news)
                .fetch()
                .size();

        return new PageImpl<>(pages, pageable, totalSize);
    }
}
