package com.project.capstone.controller;

import com.project.capstone.domain.dto.*;
import com.project.capstone.domain.dto.chat.MessageDto;
import com.project.capstone.domain.dto.post.*;
import com.project.capstone.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @GetMapping("/page")
    public ResponseEntity<Page<PageResponseDto>> pagePost(@RequestParam(name = "page") int page) {
        return ResponseEntity.ok(postService.pagePost(page));
    }

    @GetMapping("/{nickname}/{page}")
    public ResponseEntity<Page<MyPageResponseDto>> myPagePost(@PathVariable(name = "nickname") String nickname, @PathVariable(name = "page") int page) {
        return ResponseEntity.ok(postService.myPagePost(nickname, page));
    }

    @GetMapping("/one")
    public ResponseEntity<FullPostInfoDto> getOnePost(@RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(postService.onePost(id));
    }

    @PostMapping("/")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody CreatePostRequestDto request) {
        return ResponseEntity.ok(postService.createPost(request.getTitle(), request.getContent()));
    }

    @GetMapping("/change")
    public ResponseEntity<FullPostInfoDto> getChangePost(@RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(postService.onePost(id));
    }

    @PutMapping("/")
    public ResponseEntity<PostResponseDto> putChangePost(@RequestBody ChangePostRequestDto request) {
        return ResponseEntity.ok(postService.changePost(request.getId(), request.getTitle(), request.getContent()));
    }

    @DeleteMapping("/one")
    public ResponseEntity<MessageDto> deletePost(@RequestParam(name = "id") Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(new MessageDto("Success"));
    }
}
