package com.project.capstone.repository;

import com.project.capstone.domain.dto.NewsResponseDto;
import com.project.capstone.domain.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsRepositoryCustom {

    Page<NewsResponseDto> searchAllNews(Team team, Pageable pageable);
}
