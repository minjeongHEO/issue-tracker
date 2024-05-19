package com.issuetracker.issue.service;

import com.issuetracker.issue.domain.Issue;
import com.issuetracker.issue.dto.IssueCountDto;
import com.issuetracker.issue.exception.IssueNotFoundException;
import com.issuetracker.issue.repository.IssueAssigneeRepository;
import com.issuetracker.issue.repository.IssueLabelRepository;
import com.issuetracker.issue.repository.IssueRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class IssueQueryService {
    private final IssueRepository issueRepository;
    private final IssueAssigneeRepository issueAssigneeRepository;
    private final IssueLabelRepository issueLabelRepository;

    /**
     * 열린 이슈의 개수와 닫힌 이슈의 개수를 구한다.
     */
    @Transactional(readOnly = true)
    public IssueCountDto countIssues() {
        long openedIssueCount = issueRepository.countAllByIsClosed(false);
        long closedIssueCount = issueRepository.countAllByIsClosed(true);

        return new IssueCountDto(openedIssueCount, closedIssueCount);
    }

    /**
     * 특정 이슈에 있는 라벨들의 아이디 리스트를 반환한다.
     */
    @Transactional(readOnly = true)
    public List<Long> findLabelIdsByIssueId(Long issueId) {
        return issueLabelRepository.findAllByIssueId(issueId);
    }

    /**
     * 특정 이슈를 담당하는 담당자들의 아이디 리스트를 반환한다.
     */
    @Transactional(readOnly = true)
    public List<String> findAssigneeIdsByIssueId(Long issueId) {
        return issueAssigneeRepository.findAssigneeIdsByIssueId(issueId);
    }

    @Transactional(readOnly = true)
    public Issue getIssueOrThrow(Long id) {
        return issueRepository.findById(id).orElseThrow(IssueNotFoundException::new);
    }
}
