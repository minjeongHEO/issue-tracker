package com.issuetracker.member.service;

import com.issuetracker.member.dto.MemberCreateDto;
import com.issuetracker.member.exception.DuplicateMemberException;
import com.issuetracker.member.model.Member;
import com.issuetracker.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 멤버의 아이디가 중복이 아니라면 새로운 멤버를 생성한다.
     */
    public Member create(MemberCreateDto memberCreateDto) {
        validateDuplicateId(memberCreateDto);

        // 정상 로직
        Member member = getMemberByMemberCreateDto(memberCreateDto);

        Member created = memberRepository.insert(member);
        log.info("새로운 유저가 생성되었습니다. {}", created);
        return created;
    }

    private void validateDuplicateId(MemberCreateDto memberCreateDto) {
        if (memberRepository.existsById(memberCreateDto.getId())) {
            throw new DuplicateMemberException();
        }
    }

    private Member getMemberByMemberCreateDto(MemberCreateDto memberCreateDto) {
        return new Member(memberCreateDto.getId(), memberCreateDto.getPassword(),
                memberCreateDto.getNickname(), memberCreateDto.getEmail());
    }
}
