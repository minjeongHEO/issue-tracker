package com.issuetracker.issue.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class IssueCreateRequest {
    @NotBlank
    private final String title;
    private final String content;
    @NotBlank
    private final String authorId; // 아직 프론트에 토큰 전송 기능이 없으므로 사용자에게 입력받음
    private final Long milestoneId;
    private final Long fileId;
    private final List<Long> labelIds;
    private final List<String> assigneeIds;
}
