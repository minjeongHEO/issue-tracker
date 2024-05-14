package com.issuetracker.issue.dto;

import com.issuetracker.label.dto.LabelCoverDto;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class IssueListDto {
    @NotBlank
    private final Long id;
    @NotBlank
    private final String title;
    @NotBlank
    private final String memberId;
    @NotBlank
    private final LocalDateTime createDate;
    private final List<LabelCoverDto> labels;
    private final String milestoneName;
}
