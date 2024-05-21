package com.issuetracker.global.dto;

import com.issuetracker.issue.dto.IssueCountDto;
import com.issuetracker.issue.dto.IssueFilterResponse;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class HomeResponse {
    private final IssueCountDto count;
    private final List<IssueFilterResponse> filteredIssues;
}
