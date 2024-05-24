package com.issuetracker.member.service;

import com.issuetracker.file.service.FileService;
import com.issuetracker.global.exception.UnauthorizedException;
import com.issuetracker.member.dto.LoginResponse;
import com.issuetracker.member.dto.LoginTryDto;
import com.issuetracker.member.dto.SimpleMemberDto;
import com.issuetracker.member.dto.TokenResponse;
import com.issuetracker.member.entity.Member;
import com.issuetracker.member.exception.LoginFailException;
import com.issuetracker.member.repository.MemberRepository;
import com.issuetracker.member.util.JwtUtil;
import com.issuetracker.member.util.MemberMapper;
import io.jsonwebtoken.Claims;
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
    private final FileService fileService;
    private final JwtUtil jwtUtil;

    /**
     * 사용자가 입력한 id, password와 대조하여 일치하면 멤버를 반환하고 아니면 예외를 발생시킵니다.
     */
    @Transactional(readOnly = true)
    public LoginResponse login(LoginTryDto loginTryDto) {
        String idValue = loginTryDto.getId();
        String passwordValue = loginTryDto.getPassword();

        Member targetMember = findTargetMember(idValue);
        validatePassword(targetMember, passwordValue);

        String imgUrl = getImgUrl(targetMember);
        SimpleMemberDto memberProfile = MemberMapper.toSimpleMemberDto(targetMember, imgUrl);

        String accessToken = jwtUtil.createAccessToken(idValue);
        String refreshToken = jwtUtil.createRefreshToken(idValue);
        TokenResponse tokenResponse = new TokenResponse(accessToken, refreshToken);

        log.info("멤버가 로그인하였습니다. {}", idValue);
        return new LoginResponse(memberProfile, tokenResponse);
    }

    public TokenResponse refreshAccessToken(String authorization) {
        String refreshToken = jwtUtil.extractJwtToken(authorization);
        if (refreshToken == null) {
            throw new UnauthorizedException();
        }
        Claims claims = jwtUtil.validateRefreshToken(refreshToken);
        String memberId = jwtUtil.extractMemberId(claims);
        // 이 사이에 redis에 저장된 id와 리프레시토큰이 일치하는지 확인해야함

        String accessToken = jwtUtil.createAccessToken(memberId);
        return new TokenResponse(accessToken, refreshToken);
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

    private String getImgUrl(Member targetMember) {
        if (targetMember.getFileId() == null) {
            return null;
        }
        return fileService.getImgUrlById(targetMember.getFileId());
    }
}
