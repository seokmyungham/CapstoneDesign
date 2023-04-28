package com.project.capstone.controller;

import com.project.capstone.domain.dto.CommentRequestDto;
import com.project.capstone.domain.dto.CommentResponseDto;
import com.project.capstone.domain.dto.MessageDto;
import com.project.capstone.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/list")
    public ResponseEntity<List<CommentResponseDto>> getComments(@RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(commentService.getComment(id));
    }

    @PostMapping("/")
    public ResponseEntity<CommentResponseDto> postComment(@RequestBody CommentRequestDto request) {
        return ResponseEntity.ok(commentService.createComment(request.getPostId(), request.getBody()));
    }

    @DeleteMapping("/one")
    public ResponseEntity<MessageDto> deleteComment(@RequestParam(name = "id") Long id) {
        commentService.removeComment(id);
        return ResponseEntity.ok(new MessageDto("Success"));
    }
}
