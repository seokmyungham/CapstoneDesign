package com.project.capstone.repository;

import com.project.capstone.domain.dto.PageResponseDto;
import com.project.capstone.domain.entity.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.capstone.domain.entity.QPost.*;

@RequiredArgsConstructor
@Repository
public class PageRepositoryImpl implements PageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PageResponseDto> searchAll(Pageable pageable) {
        List<Post> content = queryFactory
                .selectFrom(post)
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<PageResponseDto> pages = content
                .stream()
                .map(PageResponseDto::of)
                .collect(Collectors.toList());

        int totalSize = queryFactory
                .selectFrom(post)
                .fetch()
                .size();

        return new PageImpl<>(pages, pageable, totalSize);
    }

    @Override
    public Page<PageResponseDto> searchByWriter(String nickname, Pageable pageable) {
        List<Post> content = queryFactory
                .selectFrom(post)
                .where(post.member.nickname.eq(nickname))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<PageResponseDto> pages = content
                .stream()
                .map(PageResponseDto::of)
                .collect(Collectors.toList());

        int totalSize = queryFactory
                .selectFrom(post)
                .fetch()
                .size();

        return new PageImpl<>(pages, pageable, totalSize);
    }
}
