package com.issuetracker.issue.service;

import com.issuetracker.issue.controller.IssueBodyModifyDto;
import com.issuetracker.issue.domain.Issue;
import com.issuetracker.issue.domain.IssueAssignee;
import com.issuetracker.issue.domain.IssueLabel;
import com.issuetracker.issue.dto.IssueCreateRequestDto;
import com.issuetracker.issue.dto.IssueCreateResponseDto;
import com.issuetracker.issue.dto.IssueTitleModifyDto;
import com.issuetracker.issue.exception.IssueNotFoundException;
import com.issuetracker.issue.repository.IssueAssigneeRepository;
import com.issuetracker.issue.repository.IssueLabelRepository;
import com.issuetracker.issue.repository.IssueRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class IssueCudService {
    private final IssueRepository issueRepository;
    private final IssueLabelRepository issueLabelRepository;
    private final IssueAssigneeRepository issueAssigneeRepository;

    @Transactional
    public IssueCreateResponseDto createIssue(IssueCreateRequestDto request) {
        Issue issue = toIssue(request);
        Long issueId = issueRepository.save(issue).getId();

        List<Long> labelIds = request.getLabelIds();
        insertIssueLabels(labelIds, issueId);

        List<String> assigneeIds = request.getAssigneeIds();
        insertIssueAssigness(assigneeIds, issueId);

        return toIssueCreateResponseDto(issue, labelIds, assigneeIds);
    }

    @Transactional
    public void modifyIssueTitle(Long id, IssueTitleModifyDto issueTitleModifyDto) {
        validateIssueExists(id);
        String title = issueTitleModifyDto.getTitle();
        issueRepository.updateTitleById(id, title);
    }

    @Transactional
    public void modifyIssueBody(Long id, IssueBodyModifyDto issueBodyModifyDto) {
        validateIssueExists(id);
        String content = issueBodyModifyDto.getContent();
        Long fileId = issueBodyModifyDto.getFileId();
        issueRepository.updateBodyById(id, content, fileId);
    }

    private void insertIssueLabels(List<Long> labelIds, Long issueId) {
        labelIds.stream()
                .map(labelId -> new IssueLabel(issueId, labelId))
                .forEach(issueLabelRepository::insert);
    }

    private void insertIssueAssigness(List<String> assigneeIds, Long issueId) {
        assigneeIds.stream()
                .map(assigneeId -> new IssueAssignee(issueId, assigneeId))
                .forEach(issueAssigneeRepository::insert);
    }

    private Issue toIssue(IssueCreateRequestDto request) {
        return new Issue(request.getTitle(), request.getContent(), LocalDateTime.now(), false,
                request.getAuthorId(),
                request.getMilestoneId(), request.getFileId());
    }

    private IssueCreateResponseDto toIssueCreateResponseDto(Issue issue, List<Long> labelIds,
                                                            List<String> assigneeIds) {
        return new IssueCreateResponseDto(issue.getId(), issue.getTitle(), issue.getContent(), issue.getMemberId(),
                issue.getCreateDate(), issue.isClosed(), issue.getMilestoneId(), issue.getFileId(),
                labelIds, assigneeIds);
    }

    private void validateIssueExists(Long id) {
        if (!issueRepository.existsById(id)) {
            throw new IssueNotFoundException();
        }
    }
}
