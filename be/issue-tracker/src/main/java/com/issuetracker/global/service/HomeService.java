package com.issuetracker.global.service;

import com.issuetracker.global.dto.HomeComponentResponse;
import com.issuetracker.global.dto.HomeIssueResponse;
import com.issuetracker.issue.dto.IssueQueryDto;
import com.issuetracker.issue.service.IssueFilterService;
import com.issuetracker.issue.service.IssueQueryService;
import com.issuetracker.label.service.LabelService;
import com.issuetracker.milestone.service.MilestoneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeService {
    private final IssueQueryService issueQueryService;
    private final IssueFilterService issueFilterService;
    private final LabelService labelService;
    private final MilestoneService milestoneService;

    /**
     * 라벨의 개수와 마일스톤의 개수를 나타내는 홈 화면의 상위 컴포넌트를 반환한다.
     */
    @Transactional(readOnly = true)
    public HomeComponentResponse getComponents() {
        return HomeComponentResponse.builder()
                .labelCount(labelService.countLabels())
                .milestoneCount(milestoneService.countMilestones())
                .build();
    }

    /**
     * 홈 화면의 필터링된 이슈 목록을 반환한다.
     */
    @Transactional(readOnly = true)
    public HomeIssueResponse getFilteredIssues(IssueQueryDto issueQueryDto) {
        return HomeIssueResponse.builder()
                .count(issueQueryService.countIssues())
                .filteredIssues(issueFilterService.getFilteredIssues(issueQueryDto))
                .build();
    }
}
