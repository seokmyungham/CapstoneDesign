package com.project.capstone.service;

import com.project.capstone.config.SecurityUtil;
import com.project.capstone.domain.dto.RecommendDto;
import com.project.capstone.domain.entity.Member;
import com.project.capstone.domain.entity.Post;
import com.project.capstone.domain.entity.Recommend;
import com.project.capstone.repository.MemberRepository;
import com.project.capstone.repository.PostRepository;
import com.project.capstone.repository.RecommendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RecommendService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final RecommendRepository recommendRepository;

    public RecommendDto allRecommend(Post post) {
        List<Recommend> recommends = post.getRecommends();
        return getRecommendDto(recommends);
    }

    @Transactional
    public void createRecommend(Long id) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("글이 없습니다"));

        Recommend recommend = Recommend.createRecommend(member, post);
        log.info("{} -> {}번 게시글 좋아요", member.getNickname(), post.getId());
        recommendRepository.save(recommend);
    }

    @Transactional
    public void removeRecommend(Long id) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("글이 없습니다."));
        Recommend recommend = recommendRepository.findAllByPost(post)
                .stream()
                .filter(r -> r.getMember().equals(member))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("추천이 없습니다."));

        log.info("{} -> {}번 게시글 좋아요 취소", member.getNickname(), post.getId());
        recommend.removeRecommend(recommend, post);
        recommendRepository.delete(recommend);
    }

    private RecommendDto getRecommendDto(List<Recommend> recommends) {
        int size = recommends.size();
        if (size == 0) {
            return RecommendDto.noOne();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == "anonymousUser") {
            return new RecommendDto(size, false);
        } else {
            Member member = memberRepository.findById(Long.parseLong(authentication.getName())).orElseThrow();
            boolean result = recommends.stream().anyMatch(recommend -> recommend.getMember().equals(member));
            return new RecommendDto(size, result);
        }
    }
}
