package com.project.capstone.controller;

import com.project.capstone.domain.dto.chat.MessageDto;
import com.project.capstone.domain.dto.RecommendDto;
import com.project.capstone.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {
    private final RecommendService recommendService;

    @GetMapping("/list")
    public ResponseEntity<RecommendDto> getRecommends(@RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(recommendService.allRecommend(id));
    }

    @PostMapping("/")
    public ResponseEntity<MessageDto> postRecommend(@RequestBody RecommendDto dto) {
        recommendService.createRecommend(dto.getId());
        return ResponseEntity.ok(new MessageDto("Success"));
    }

    @DeleteMapping("/one")
    public ResponseEntity<MessageDto> deleteRecommend(@RequestParam(name = "id") Long id) {
        recommendService.removeRecommend(id);
        return ResponseEntity.ok(new MessageDto("Success"));
    }
}
