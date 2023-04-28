package com.project.capstone.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommendDto {
    private Long id;
    private int recommendNum;
    private boolean isRecommended;

    public RecommendDto(int recommendNum, boolean isRecommended) {
        this.recommendNum = recommendNum;
        this.isRecommended = isRecommended;
    }

    public static RecommendDto noOne() {
        return RecommendDto.builder()
                .recommendNum(0)
                .isRecommended(false)
                .build();
    }

}
