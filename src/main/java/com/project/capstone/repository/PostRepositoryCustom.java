package com.project.capstone.repository;

import com.project.capstone.domain.dto.PageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<PageResponseDto> searchAll(Pageable pageable);

    Page<PageResponseDto> searchByWriter(String nickname, Pageable pageable);
}
