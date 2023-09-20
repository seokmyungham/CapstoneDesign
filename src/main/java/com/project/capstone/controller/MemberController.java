package com.project.capstone.controller;

import com.project.capstone.domain.dto.auth.ChangePasswordRequestDto;
import com.project.capstone.domain.dto.member.MemberRequestDto;
import com.project.capstone.domain.dto.member.MemberResponseDto;
import com.project.capstone.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> getMyMemberInfo() {
        MemberResponseDto myInfoBySecurity = memberService.getMyInfoBySecurity();
        log.info("{} 님이 로그인 하였습니다.", myInfoBySecurity.getNickname());
        return ResponseEntity.ok(myInfoBySecurity);
    }

    @GetMapping("/{nickname}")
    public ResponseEntity<MemberResponseDto> getMemberInfo(@PathVariable(name = "nickname") String nickname) {
        MemberResponseDto memberInfo = memberService.getMemberInfo(nickname);
        return ResponseEntity.ok(memberInfo);
    }

    @PostMapping("/nickname")
    public ResponseEntity<MemberResponseDto> changeMemberNickname(@RequestBody MemberRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberNickname(request.getNickname()));
    }

    @PostMapping("/password")
    public ResponseEntity<MemberResponseDto> changeMemberPassword(@RequestBody ChangePasswordRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberPassword(request.getExPassword(), request.getNewPassword()));
    }

    @PostMapping("/image")
    public ResponseEntity<MemberResponseDto> changeMemberImage(@RequestBody MemberRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberImage(request.getImage()));
    }

    @PostMapping("/introduction")
    public ResponseEntity<MemberResponseDto> changeMemberIntroduction(@RequestBody MemberRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberIntroduction(request.getIntroduction()));
    }

    @GetMapping("/search/{nickname}")
    public ResponseEntity<List<MemberResponseDto>> searchMemberList(@PathVariable(name = "nickname") String nickname) {
        return ResponseEntity.ok(memberService.searchMemberList(nickname));
    }
}
