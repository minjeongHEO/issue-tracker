package com.issuetracker.issue.service;

import com.issuetracker.issue.dto.IssueAssigneeModifyDto;
import com.issuetracker.issue.dto.IssueBodyModifyDto;
import com.issuetracker.issue.dto.IssueChangeStatusDto;
import com.issuetracker.issue.dto.IssueCreateRequest;
import com.issuetracker.issue.dto.IssueCreateResponse;
import com.issuetracker.issue.dto.IssueLabelModifyDto;
import com.issuetracker.issue.dto.IssueMilestoneModifyDto;
import com.issuetracker.issue.dto.IssueTitleModifyDto;
import com.issuetracker.issue.entity.Issue;
import com.issuetracker.issue.entity.IssueAssignee;
import com.issuetracker.issue.entity.IssueLabel;
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

    /**
     * 사용자가 입력한 정보를 바탕으로 이슈를 생성한다.
     */
    @Transactional
    public IssueCreateResponse createIssue(IssueCreateRequest request) {
        Issue issue = toIssue(request);
        Issue saved = issueRepository.save(issue);

        List<Long> labelIds = request.getLabelIds();
        insertIssueLabels(labelIds, saved.getId());

        List<String> assigneeIds = request.getAssigneeIds();
        insertIssueAssigness(assigneeIds, saved.getId());

        return toIssueCreateResponseDto(saved, labelIds, assigneeIds);
    }

    /**
     * id와 일치하는 이슈를 찾아 제목을 변경한다. 해당 이슈가 존재하지 않는다면 예외를 발생시킨다.
     */
    @Transactional
    public void modifyIssueTitle(Long id, IssueTitleModifyDto issueTitleModifyDto) {
        validateIssueExists(id);
        String title = issueTitleModifyDto.getTitle();
        issueRepository.updateTitleById(id, title);
    }

    /**
     * id와 일치하는 이슈를 찾아 본문을 변경한다. 해당 이슈가 존재하지 않는다면 예외를 발생시킨다.
     */
    @Transactional
    public void modifyIssueBody(Long id, IssueBodyModifyDto issueBodyModifyDto) {
        validateIssueExists(id);
        String content = issueBodyModifyDto.getContent();
        Long fileId = issueBodyModifyDto.getFileId();
        issueRepository.updateBodyById(id, content, fileId);
    }

    /**
     * id와 일치하는 이슈를 찾아 라벨을 변경한다. 해당 이슈가 존재하지 않는다면 예외를 발생시킨다.
     */
    @Transactional
    public void modifyIssueLabel(Long id, IssueLabelModifyDto issueLabelModifyDto) {
        validateIssueExists(id);
        issueLabelRepository.deleteByIssueId(id);
        List<Long> labelIds = issueLabelModifyDto.getLabelIds();
        insertIssueLabels(labelIds, id);
    }

    /**
     * id와 일치하는 이슈를 찾아 담당자를 변경한다. 해당 이슈가 존재하지 않는다면 예외를 발생시킨다.
     */
    @Transactional
    public void modifyIssueAssignee(Long id, IssueAssigneeModifyDto issueAssigneeModifyDto) {
        validateIssueExists(id);
        issueAssigneeRepository.deleteByIssueId(id);
        List<String> assigneeIds = issueAssigneeModifyDto.getAssigneeIds();
        insertIssueAssigness(assigneeIds, id);
    }

    /**
     * id와 일치하는 이슈를 찾아 마일스톤을 변경한다. 해당 이슈가 존재하지 않는다면 예외를 발생시킨다.
     */
    @Transactional
    public void modifyIssueMilestone(Long id, IssueMilestoneModifyDto issueMilestoneModifyDto) {
        validateIssueExists(id);
        Long milestoneId = issueMilestoneModifyDto.getMilestoneId();
        issueRepository.updateMilestoneById(id, milestoneId);
    }

    /**
     * 요청한 id와 일치하는 이슈를 연다.
     */
    @Transactional
    public void openIssues(IssueChangeStatusDto issueChangeStatusDto) {
        List<Long> issueIds = issueChangeStatusDto.getIssueIds();
        if (issueIds == null) {
            return;
        }
        issueIds.forEach(issueId -> issueRepository.changeStatusById(issueId, false));
    }

    /**
     * 요청한 id와 일치하는 이슈를 닫는다.
     */
    @Transactional
    public void closeIssues(IssueChangeStatusDto issueChangeStatusDto) {
        List<Long> issueIds = issueChangeStatusDto.getIssueIds();
        if (issueIds == null) {
            return;
        }
        issueIds.forEach(issueId -> issueRepository.changeStatusById(issueId, true));
    }

    /**
     * id와 일치하는 이슈를 삭제한다. 이슈가 존재하지 않는다면 예외를 발생시킨다.
     */
    @Transactional
    public void deleteIssue(Long id) {
        validateIssueExists(id);
        issueRepository.deleteById(id);
    }

    private void insertIssueLabels(List<Long> labelIds, Long issueId) {
        if (labelIds == null) {
            return;
        }
        labelIds.stream()
                .map(labelId -> new IssueLabel(issueId, labelId))
                .forEach(issueLabelRepository::insert);
    }

    private void insertIssueAssigness(List<String> assigneeIds, Long issueId) {
        if (assigneeIds == null) {
            return;
        }
        assigneeIds.stream()
                .map(assigneeId -> new IssueAssignee(issueId, assigneeId))
                .forEach(issueAssigneeRepository::insert);
    }

    private Issue toIssue(IssueCreateRequest request) {
        return new Issue(null, request.getTitle(), request.getContent(), LocalDateTime.now(), false,
                request.getAuthorId(),
                request.getMilestoneId(), request.getFileId());
    }

    private IssueCreateResponse toIssueCreateResponseDto(Issue issue, List<Long> labelIds,
                                                         List<String> assigneeIds) {
        return new IssueCreateResponse(issue.getId(), issue.getTitle(), issue.getContent(), issue.getMemberId(),
                issue.getCreateDate(), issue.isClosed(), issue.getMilestoneId(), issue.getFileId(),
                labelIds, assigneeIds);
    }

    private void validateIssueExists(Long id) {
        if (!issueRepository.existsById(id)) {
            throw new IssueNotFoundException();
        }
    }
}
