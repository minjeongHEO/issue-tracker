package com.issuetracker.issue.dto;

import com.issuetracker.label.dto.LabelCoverDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class IssueFilterResponseDto {
    private final Long id;
    private final String title;
    private final String authorId;
    private final LocalDateTime createDate;
    private final List<String> assigneeIds;
    private final List<LabelCoverDto> labels;
    private final String milestoneName;
}
