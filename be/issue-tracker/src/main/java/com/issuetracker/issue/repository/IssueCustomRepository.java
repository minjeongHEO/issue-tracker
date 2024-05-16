package com.issuetracker.issue.repository;

import com.issuetracker.issue.dto.IssueFilterDto;
import com.issuetracker.issue.dto.IssueQueryDto;
import java.util.Map;
import java.util.Set;

public interface IssueCustomRepository {
    Set<IssueFilterDto> findIssueWithFilter(Map<String, Object> filter, IssueQueryDto issueQueryDto);
}
