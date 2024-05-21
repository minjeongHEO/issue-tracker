package com.issuetracker.issue.repository;

import com.issuetracker.issue.dto.IssueFilterResponse;
import com.issuetracker.issue.dto.IssueQueryDto;
import java.util.Map;
import java.util.Set;

public interface IssueCustomRepository {
    Set<IssueFilterResponse> findIssueWithFilter(Map<String, Object> filter, IssueQueryDto issueQueryDto);
}
