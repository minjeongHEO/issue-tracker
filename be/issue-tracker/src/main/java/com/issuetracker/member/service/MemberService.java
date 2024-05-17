package com.issuetracker.member.service;

import com.issuetracker.file.dto.UploadedFileDto;
import com.issuetracker.file.service.FileService;
import com.issuetracker.member.dto.MemberCreateDto;
import com.issuetracker.member.dto.SimpleMemberDto;
import com.issuetracker.member.exception.MemberNotFoundException;
import com.issuetracker.member.model.Member;
import com.issuetracker.member.repository.MemberRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final FileService fileService;

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

    @Transactional
    public List<SimpleMemberDto> getMembers() {
        List<Member> members = (List<Member>) memberRepository.findAll();
        List<SimpleMemberDto> simpleMembers = toSimpleMemberDtos(members);
        return Collections.unmodifiableList(simpleMembers);
    }

    @Transactional
    public SimpleMemberDto getSimpleMemberDtoById(String id) {
        Member member = getMemberOrThrow(id);
        String imgUrl = getImgUrl(member);
        return new SimpleMemberDto(id, imgUrl);
    }

    public List<Member> findMembersById(List<String> issueAssigneeIds) {
        return (List<Member>) memberRepository.findAllById(issueAssigneeIds);
    }

    private Member getMemberOrThrow(String id) {
        return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
    }

    private Member toMember(MemberCreateDto memberCreateDto) {
        return new Member(memberCreateDto.getId(), memberCreateDto.getPassword(),
                memberCreateDto.getNickname(), memberCreateDto.getEmail(), null);
    }

    private List<SimpleMemberDto> toSimpleMemberDtos(List<Member> members) {
        List<SimpleMemberDto> simpleMemberDtos = new ArrayList<>();
        for (Member member : members) {
            String imgUrl = getImgUrl(member);
            simpleMemberDtos.add(new SimpleMemberDto(member.getId(), imgUrl));
        }
        return simpleMemberDtos;
    }

    private String getImgUrl(Member member) {
        if (member.getFileId() == null) {
            return null;
        }
        UploadedFileDto uploadedFileDto = fileService.showFile(member.getFileId());
        return uploadedFileDto.getUrl();
    }
}
