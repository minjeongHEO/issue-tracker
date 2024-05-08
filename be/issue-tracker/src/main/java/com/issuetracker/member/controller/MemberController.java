package com.issuetracker.member.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.issuetracker.member.dto.MemberCreateDto;
import com.issuetracker.member.model.Member;
import com.issuetracker.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Member> createMember(@Validated @RequestBody MemberCreateDto memberCreateDto,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("필드 오류가 발생하였습니다. {}", bindingResult);
            return ResponseEntity.status(BAD_REQUEST).build();
        }

        Member member = memberService.create(memberCreateDto);
        return ResponseEntity.ok().body(member);
    }
}
