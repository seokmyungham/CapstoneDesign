package com.project.capstone.repository;

import com.project.capstone.domain.dto.PageResponseDto;
import com.project.capstone.domain.dto.post.MyPageResponseDto;
import com.project.capstone.domain.entity.Post;
import com.project.capstone.domain.entity.QRecommend;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.capstone.domain.entity.QMember.*;
import static com.project.capstone.domain.entity.QPost.*;

@RequiredArgsConstructor
@Repository
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Post> searchAll(Pageable pageable) {
        List<Post> content = queryFactory
                .selectFrom(post)
                .join(post.member, member).fetchJoin()
                .leftJoin(post.recommends, QRecommend.recommend).fetchJoin()
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int totalSize = queryFactory
                .selectFrom(post)
                .fetch()
                .size();

        return new PageImpl<>(content, pageable, totalSize);
    }

    @Override
    public Page<MyPageResponseDto> searchByWriter(String nickname, Pageable pageable) {
        List<Post> content = queryFactory
                .selectFrom(post)
                .join(post.member, member).fetchJoin()
                .where(post.member.nickname.eq(nickname))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<MyPageResponseDto> pages = content
                .stream()
                .map(MyPageResponseDto::of)
                .collect(Collectors.toList());

        int totalSize = queryFactory
                .selectFrom(post)
                .where(post.member.nickname.eq(nickname))
                .fetch()
                .size();

        return new PageImpl<>(pages, pageable, totalSize);
    }
}
