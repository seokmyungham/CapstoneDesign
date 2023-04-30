package com.project.capstone.repository;

import com.project.capstone.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<Post, Long>, PageRepositoryCustom {
}
