package com.issuetracker.member.service;

import com.issuetracker.member.dto.LoginMemberDto;
import com.issuetracker.member.dto.LoginTryDto;
import com.issuetracker.member.exception.LoginFailException;
import com.issuetracker.member.model.Member;
import com.issuetracker.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final MemberRepository memberRepository;

    /**
     * 사용자가 입력한 id, password와 대조하여 일치하면 멤버를 반환하고 아니면 예외를 발생시킵니다.
     */
    @Transactional(readOnly = true)
    public LoginMemberDto login(LoginTryDto loginTryDto) {
        String idValue = loginTryDto.getId();
        String passwordValue = loginTryDto.getPassword();

        Member targetMember = findTargetMember(idValue);
        validatePassword(targetMember, passwordValue);

        LoginMemberDto loginMember = toLoginMemberDto(targetMember);
        log.info("멤버가 로그인하였습니다. {}", loginMember.getId());
        return loginMember;
    }

    private Member findTargetMember(String idValue) {
        Optional<Member> optionalMember = memberRepository.findById(idValue);
        if (optionalMember.isEmpty()) {
            throw new LoginFailException();
        }
        return optionalMember.get();
    }

    private void validatePassword(Member targetMember, String passwordValue) {
        if (!targetMember.hasSamePassword(passwordValue)) {
            throw new LoginFailException();
        }
    }

    private LoginMemberDto toLoginMemberDto(Member targetMember) {
        return new LoginMemberDto(targetMember.getId(), targetMember.getNickname(),
                targetMember.getEmail());
    }
}
