package com.issuetracker.issue.service;

import com.issuetracker.global.util.IssueFilterQueryGenerator;
import com.issuetracker.issue.dto.IssueCountDto;
import com.issuetracker.issue.dto.IssueFilterResponse;
import com.issuetracker.issue.dto.IssueFilterResult;
import com.issuetracker.issue.dto.IssueQueryDto;
import com.issuetracker.issue.repository.IssueCustomRepository;
import com.issuetracker.issue.util.IssueMapper;
import com.issuetracker.label.dto.LabelCoverDto;
import com.issuetracker.label.service.LabelService;
import com.issuetracker.member.dto.SimpleMemberDto;
import com.issuetracker.member.service.MemberService;
import com.issuetracker.milestone.service.MilestoneService;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class IssueFilterService {
    private final IssueQueryService issueQueryService;
    private final LabelService labelService;
    private final MilestoneService milestoneService;
    private final MemberService memberService;
    private final IssueCustomRepository issueCustomRepository;

    /**
     * 사용자가 설정한 조건에 따라 필터링한다.
     */
    @Transactional(readOnly = true)
    public Set<IssueFilterResult> filterIssues(IssueQueryDto issueQueryDto) {
        Long labelId = labelService.findIdByName(issueQueryDto.getLabelName());
        Long milestoneId = milestoneService.findIdByName(issueQueryDto.getMilestoneName());

        // 사용자가 전달한 필터 조건으로 쿼리와 파라미터를 동적으로 생성
        IssueFilterQueryGenerator filterQueryGenerator = new IssueFilterQueryGenerator(issueQueryDto);
        Map<String, Object> filter = filterQueryGenerator.generate(labelId, milestoneId);
        return issueCustomRepository.findIssueWithFilter(filter, issueQueryDto);
    }

    /**
     * 필터링 된 이슈의 열린 개수와 닫힌 개수를 구한다.
     */
    public IssueCountDto countFilteredIssues(Set<IssueFilterResult> filteredIssues) {
        long openedCount = filteredIssues.stream().filter(issue -> issue.getIsClosed().equals(false)).toList().size();
        long closedCount = filteredIssues.stream().filter(issue -> issue.getIsClosed().equals(true)).toList().size();
        return new IssueCountDto(openedCount, closedCount);
    }

    /**
     * 필터링된 이슈에 열림/닫힘 조건을 적용하여 assignee와 label 리스트를 구한다.
     */
    @Transactional(readOnly = true)
    public List<IssueFilterResponse> getIssueFilterResponse(Boolean isClosed, Set<IssueFilterResult> filteredIssues) {
        List<IssueFilterResult> filteredIssuesWithClosedCondition = applyClosedCondition(isClosed, filteredIssues);
        return filteredIssuesWithClosedCondition.stream()
                .map(result -> {
                    SimpleMemberDto author = getAuthor(result.getAuthorId());
                    List<SimpleMemberDto> assignees = getAssignees(result.getId());
                    List<LabelCoverDto> labels = getLabels(result.getId());
                    return IssueMapper.toIssueFilterResponse(result, author, assignees, labels);
                })
                .collect(Collectors.toList());
    }

    private List<IssueFilterResult> applyClosedCondition(Boolean isClosed, Set<IssueFilterResult> filteredIssues) {
        return filteredIssues.stream().filter(filterResult -> filterResult.getIsClosed().equals(isClosed)).toList();
    }

    private SimpleMemberDto getAuthor(String authorId) {
        return memberService.getSimpleMemberById(authorId);
    }

    private List<SimpleMemberDto> getAssignees(Long filterId) {
        List<String> assigneeIds = issueQueryService.findAssigneeIdsByIssueId(filterId);
        return assigneeIds.stream()
                .map(memberService::getSimpleMemberById)
                .collect(Collectors.toList());
    }

    private List<LabelCoverDto> getLabels(Long filterId) {
        List<Long> labelIds = issueQueryService.findLabelIdsByIssueId(filterId);
        if (!labelIds.isEmpty()) {
            return labelService.findLabelCoverDtoByIds(labelIds);
        }
        return List.of();
    }
}
