package com.issuetracker.member.service;

import com.issuetracker.member.dto.MemberCreateDto;
import com.issuetracker.member.model.Member;
import com.issuetracker.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 멤버의 아이디가 중복이 아니라면 새로운 멤버를 생성한다.
     */
    @Transactional
    public Member create(MemberCreateDto memberCreateDto) {
        Member member = toMember(memberCreateDto);
        Member created = memberRepository.insert(member);

        log.info("새로운 유저가 생성되었습니다. {}", created);
        return created;
    }

    private Member toMember(MemberCreateDto memberCreateDto) {
        return new Member(memberCreateDto.getId(), memberCreateDto.getPassword(),
                memberCreateDto.getNickname(), memberCreateDto.getEmail());
    }
}
