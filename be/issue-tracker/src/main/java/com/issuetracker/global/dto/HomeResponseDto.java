package com.issuetracker.global.dto;

import com.issuetracker.issue.dto.IssueCountDto;
import com.issuetracker.issue.dto.IssueFilterResponseDto;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class HomeResponseDto {
    private final IssueCountDto count;
    private final List<IssueFilterResponseDto> filteredIssues;
}
