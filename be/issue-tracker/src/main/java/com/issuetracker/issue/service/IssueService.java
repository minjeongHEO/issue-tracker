package com.issuetracker.issue.service;

import com.issuetracker.issue.domain.Issue;
import com.issuetracker.issue.dto.IssueCountDto;
import com.issuetracker.issue.dto.IssueListDto;
import com.issuetracker.issue.dto.IssueListDto.IssueListDtoBuilder;
import com.issuetracker.issue.repository.IssueLabelRepository;
import com.issuetracker.issue.repository.IssueRepository;
import com.issuetracker.label.dto.LabelCoverDto;
import com.issuetracker.label.repository.LabelRepository;
import com.issuetracker.milestone.Repository.MilestoneRepository;
import com.issuetracker.milestone.domain.Milestone;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class IssueService {
    private final IssueRepository issueRepository;
    private final IssueLabelRepository issueLabelRepository;
    private final MilestoneRepository milestoneRepository;
    private final LabelRepository labelRepository;

    @Transactional(readOnly = true)
    public List<IssueListDto> getIssuesByIsClosed(boolean isClosed) {
        List<Issue> selectedIssues = issueRepository.findAllByIsClosed(isClosed);
        return selectedIssues.stream()
                .map(this::toIssueListDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public IssueCountDto getIssueCountDto() {
        long openedIssueCount = issueRepository.countAllByIsClosed(false);
        long closedIssueCount = issueRepository.countAllByIsClosed(true);

        return new IssueCountDto(openedIssueCount, closedIssueCount);
    }

    private IssueListDto toIssueListDto(Issue issue) {
        IssueListDtoBuilder builder = IssueListDto.builder()
                .id(issue.getId())
                .title(issue.getTitle())
                .memberId(issue.getMemberId())
                .createDate(issue.getCreateDate());

        addLabels(builder, issue.getId());
        addMilestone(builder, issue.getMilestoneId());
        return builder.build();
    }

    private void addLabels(IssueListDtoBuilder builder, Long issueId) {
        List<Long> labelIds = issueLabelRepository.findAllByIssueId(issueId);
        if (!labelIds.isEmpty()) {
            List<LabelCoverDto> labelCovers = labelRepository.findLabelCoverDtoById(labelIds);
            builder.labels(labelCovers);
        }
    }

    private void addMilestone(IssueListDtoBuilder builder, Long milestoneId) {
        if (milestoneId != null) {
            milestoneRepository.findById(milestoneId)
                    .map(Milestone::getName)
                    .ifPresent(builder::milestoneName);
        }
    }
}
