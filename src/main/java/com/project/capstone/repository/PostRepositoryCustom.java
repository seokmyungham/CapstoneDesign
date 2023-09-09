package com.project.capstone.repository;

import com.project.capstone.domain.dto.post.MyPageResponseDto;
import com.project.capstone.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<Post> searchAll(Pageable pageable);

    Page<MyPageResponseDto> searchByWriter(String nickname, Pageable pageable);
}
