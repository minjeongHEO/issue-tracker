package com.issuetracker.issue.dto;

import com.issuetracker.label.dto.LabelCoverDto;
import com.issuetracker.member.dto.SimpleMemberDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class IssueFilterResponse {
    private final Long id;
    private final String title;
    private final String authorId;
    private final LocalDateTime createDate;
    private final List<SimpleMemberDto> assignees;
    private final List<LabelCoverDto> labels;
    private final String milestoneName;
}