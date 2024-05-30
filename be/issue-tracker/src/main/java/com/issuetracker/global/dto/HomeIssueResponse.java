package com.issuetracker.global.dto;

import com.issuetracker.issue.dto.IssueCountDto;
import com.issuetracker.issue.dto.IssueFilterResponse;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class HomeIssueResponse {
    private final IssueCountDto count;
    private final List<IssueFilterResponse> filteredIssues;
}
