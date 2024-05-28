package com.issuetracker.member.util;

import com.issuetracker.member.dto.MemberCreateDto;
import com.issuetracker.member.dto.SimpleMemberDto;
import com.issuetracker.member.entity.Member;

public class MemberMapper {

    public static Member toMember(MemberCreateDto memberCreateDto) {
        return new Member(memberCreateDto.getId(), memberCreateDto.getPassword(),
                memberCreateDto.getNickname(), memberCreateDto.getEmail(), null);
    }

    public static SimpleMemberDto toSimpleMemberDto(Member member, String imgUrl) {
        return new SimpleMemberDto(member.getId(), imgUrl);
    }

    public static SimpleMemberDto toDeactivatedMember() {
        return new SimpleMemberDto(null, null);
    }
}
