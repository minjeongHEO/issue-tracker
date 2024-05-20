package com.issuetracker.issue.utils;

import com.issuetracker.issue.dto.IssueFilterDto;
import com.issuetracker.issue.dto.IssueFilterResponse;
import com.issuetracker.label.dto.LabelCoverDto;
import com.issuetracker.member.dto.SimpleMemberDto;
import java.util.List;

public class IssueFilterResponseDtoMapper {
    public static IssueFilterResponse toIssueFilterResponse(IssueFilterDto filterDto,
                                                            List<SimpleMemberDto> simpleMemberDtos,
                                                            List<LabelCoverDto> labelCoverDtos) {
        Long filterId = filterDto.getId();
        return IssueFilterResponse.builder()
                .id(filterId)
                .title(filterDto.getTitle())
                .authorId(filterDto.getAuthorId())
                .createDate(filterDto.getCreateDate())
                .assignees(simpleMemberDtos)
                .labels(labelCoverDtos)
                .milestoneName(filterDto.getMilestoneName())
                .build();
    }
}
