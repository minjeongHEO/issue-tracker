package com.issuetracker.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.issuetracker.member.dto.MemberCreateDto;
import com.issuetracker.member.exception.DuplicateMemberException;
import com.issuetracker.member.model.Member;
import com.issuetracker.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("멤버 생성에 성공하면 생성된 멤버 객체를 반환한다.")
    void createMember_Success() {
        // given
        MemberCreateDto memberCreateDto = new MemberCreateDto("sangchu", "123", "상추", "sangchu@gmail.com");
        Member expectedMember = new Member("sangchu", "123", "상추", "sangchu@gmail.com");

        when(memberRepository.existsById(anyString())).thenReturn(false);
        when(memberRepository.insert(any(Member.class))).thenReturn(expectedMember);

        // when
        Member actualMember = memberService.create(memberCreateDto);

        // then
        assertThat(actualMember).isNotNull()
                .extracting(Member::getId, Member::getPassword, Member::getNickname, Member::getEmail)
                .containsExactly(expectedMember.getId(), expectedMember.getPassword(), expectedMember.getNickname(),
                        expectedMember.getEmail());
    }

    @Test
    @DisplayName("멤버 생성시 id가 중복되면 예외가 발생한다.")
    void createMember_DuplicateMemberException() {
        // given
        MemberCreateDto memberCreateDto = new MemberCreateDto("sangchu", "123", "상추", "sangchu@gmail.com");

        when(memberRepository.existsById(anyString())).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> memberService.create(memberCreateDto))
                .isInstanceOf(DuplicateMemberException.class);
    }
}
