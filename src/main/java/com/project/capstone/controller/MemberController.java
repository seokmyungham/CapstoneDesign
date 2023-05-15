package com.project.capstone.controller;

import com.project.capstone.config.dto.ChangePasswordRequestDto;
import com.project.capstone.config.dto.MemberRequestDto;
import com.project.capstone.config.dto.MemberResponseDto;
import com.project.capstone.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> getMyMemberInfo() {
        MemberResponseDto myInfoBySecurity = memberService.getMyInfoBySecurity();
        log.info(myInfoBySecurity.getNickname() + "님이 로그인 하였습니다.");
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
}
