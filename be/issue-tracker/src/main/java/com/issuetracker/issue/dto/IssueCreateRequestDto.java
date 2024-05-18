package com.issuetracker.issue.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class IssueCreateRequestDto {
    private final String title;
    private final String content;
    private final String authorId; // 아직 로그인 유지 기능이 없으므로 사용자에게 입력받음
    private final Long milestoneId;
    private final Long fileId;
    private final List<Long> labelIds;
    private final List<String> assigneeIds;
}
