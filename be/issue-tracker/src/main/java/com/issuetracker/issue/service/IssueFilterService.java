package com.issuetracker.issue.service;

import com.issuetracker.global.util.IssueFilterQueryGenerator;
import com.issuetracker.issue.dto.IssueFilterResponse;
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
     * 사용자가 설정한 필터조건에 따라 필터링된 이슈 리스트를 반환한다.
     */
    @Transactional(readOnly = true)
    public List<IssueFilterResponse> getFilteredIssues(IssueQueryDto issueQueryDto) {
        // assignee와 label리스트 결과를 제외한 IssueFilterResponse
        Set<IssueFilterResponse> unfinishedFilterResponses = findIssueWithFilter(issueQueryDto);
        return unfinishedFilterResponses.stream()
                .map(filterResponse -> {
                    Long filterId = filterResponse.getId();
                    SimpleMemberDto author = getAuthor(filterId);
                    List<SimpleMemberDto> assignees = getAssignees(filterId);
                    List<LabelCoverDto> labels = getLabels(filterId);
                    return IssueMapper.toIssueFilterResponse(filterResponse, author, assignees, labels);
                })
                .collect(Collectors.toList());
    }

    private Set<IssueFilterResponse> findIssueWithFilter(IssueQueryDto issueQueryDto) {
        Long labelId = labelService.findIdByName(issueQueryDto.getLabelName());
        Long milestoneId = milestoneService.findIdByName(issueQueryDto.getMilestoneName());

        // 사용자가 전달한 필터 조건으로 쿼리와 파라미터를 동적으로 생성
        IssueFilterQueryGenerator filterQueryGenerator = new IssueFilterQueryGenerator(issueQueryDto);
        Map<String, Object> filter = filterQueryGenerator.generate(labelId, milestoneId);
        return issueCustomRepository.findIssueWithFilter(filter, issueQueryDto);
    }

    private SimpleMemberDto getAuthor(Long filterId) {
        String authorId = issueQueryService.findAuthorIdByIssueId(filterId);
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
