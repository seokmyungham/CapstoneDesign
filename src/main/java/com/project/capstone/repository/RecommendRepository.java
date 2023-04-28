package com.project.capstone.repository;

import com.project.capstone.domain.entity.Post;
import com.project.capstone.domain.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendRepository extends JpaRepository<Recommend, Long> {
    List<Recommend> findAllByPost(Post post);
}
