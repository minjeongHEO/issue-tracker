package com.issuetracker.member.service;

import com.issuetracker.member.dto.LoginTryDto;
import com.issuetracker.member.exception.LoginFailException;
import com.issuetracker.member.model.Member;
import com.issuetracker.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final MemberRepository memberRepository;

    /**
     * 사용자가 입력한 id, password와 대조하여 일치하면 멤버를 반환하고 아니면 예외를 발생시킵니다.
     */
    public Member login(LoginTryDto loginTryDto) {
        String tryId = loginTryDto.getId();
        String tryPassword = loginTryDto.getPassword();

        Member loginMember = memberRepository.findById(loginTryDto.getId()).stream()
                .filter(member -> member.hasSameId(tryId))
                .filter(member -> member.hasSamePassword(tryPassword))
                .findAny()
                .orElseThrow(LoginFailException::new);

        log.info("멤버가 로그인하였습니다. {}", loginMember);
        return loginMember;
    }
}
