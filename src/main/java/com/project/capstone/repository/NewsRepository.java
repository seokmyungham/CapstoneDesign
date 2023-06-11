package com.project.capstone.repository;

import com.project.capstone.domain.entity.News;
import com.project.capstone.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long>, NewsRepositoryCustom {

    boolean existsByHeadlineAndTeam(String headline, Team team);
}
