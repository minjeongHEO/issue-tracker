package com.issuetracker.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.issuetracker.member.dto.LoginMemberDto;
import com.issuetracker.member.dto.LoginTryDto;
import com.issuetracker.member.entity.Member;
import com.issuetracker.member.exception.LoginFailException;
import com.issuetracker.member.repository.MemberRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class LoginServiceTest {
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("아이디와 비밀번호가 일치하면 로그인에 성공하고 멤버를 반환한다.")
    void login_success() {
        // Given
        String id = "john.doe";
        String password = "password123";
        Member member = new Member(id, password, "johnny", "john.doe@example.com", null);

        // MemberRepository Mock 설정
        when(memberRepository.findById(id)).thenReturn(Optional.of(member));

        LoginTryDto loginTryDto = new LoginTryDto(id, password);

        // When
        LoginMemberDto loginMember = loginService.login(loginTryDto);

        // Then
        assertThat(member.getId()).isEqualTo(loginMember.getId());
        assertThat(member.getNickname()).isEqualTo(loginMember.getNickname());
        assertThat(member.getEmail()).isEqualTo(loginMember.getEmail());
    }

    @Test
    @DisplayName("id와 일치하는 멤버를 찾을 수 없으면 예외가 발생한다.")
    void loginFail_IdNotFound() {
        // Given
        String id = "unknown";
        String password = "password123";

        // MemberRepository Mock 설정 (비어있는 Optional 반환)
        when(memberRepository.findById(id)).thenReturn(Optional.empty());

        LoginTryDto loginTryDto = new LoginTryDto(id, password);

        // When & Then
        assertThatThrownBy(() -> loginService.login(loginTryDto))
                .isInstanceOf(LoginFailException.class);
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 예외가 발생한다.")
    void loginFail_PasswordMismatch() {
        // Given
        String id = "john.doe";
        String correctPassword = "correct_password";
        String wrongPassword = "wrong_password";
        Member member = new Member(id, correctPassword, "johnny", "john.doe@example.com", null);

        // MemberRepository Mock 설정 (올바른 ID로 멤버 반환)
        when(memberRepository.findById(id)).thenReturn(Optional.of(member));

        LoginTryDto loginTryDto = new LoginTryDto(id, wrongPassword);

        // When & Then
        assertThatThrownBy(() -> loginService.login(loginTryDto))
                .isInstanceOf(LoginFailException.class);
    }
}