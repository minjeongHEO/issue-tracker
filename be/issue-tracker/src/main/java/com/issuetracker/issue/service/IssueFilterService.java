package com.issuetracker.issue.service;

import com.issuetracker.global.utils.IssueFilterQueryGenerator;
import com.issuetracker.issue.dto.IssueFilterDto;
import com.issuetracker.issue.dto.IssueFilterResponseDto;
import com.issuetracker.issue.dto.IssueQueryDto;
import com.issuetracker.issue.repository.IssueCustomRepository;
import com.issuetracker.label.dto.LabelCoverDto;
import com.issuetracker.label.service.LabelService;
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
    private final IssueCustomRepository issueCustomRepository;

    /**
     * 사용자가 설정한 필터조건에 따라 필터링된 이슈 리스트를 반환한다.
     */
    @Transactional(readOnly = true)
    public List<IssueFilterResponseDto> getFilteredIssues(IssueQueryDto issueQueryDto) {
        Set<IssueFilterDto> issueWithFilter = findIssueWithFilter(issueQueryDto);
        return issueWithFilter.stream()
                .map(this::buildIssueFilterResponseDto)
                .collect(Collectors.toList());
    }

    private IssueFilterResponseDto buildIssueFilterResponseDto(IssueFilterDto filterDto) {
        Long filterId = filterDto.getId();
        return IssueFilterResponseDto.builder()
                .id(filterId)
                .title(filterDto.getTitle())
                .authorId(filterDto.getAuthorId())
                .createDate(filterDto.getCreateDate())
                .assigneeIds(issueQueryService.findAssigneeIdsByIssueId(filterId))
                .labels(getLabels(filterId))
                .milestoneName(filterDto.getMilestoneName())
                .build();
    }

    private Set<IssueFilterDto> findIssueWithFilter(IssueQueryDto issueQueryDto) {
        Long labelId = labelService.findIdByName(issueQueryDto.getLabelName());
        Long milestoneId = milestoneService.findIdByName(issueQueryDto.getMilestoneName());

        // 사용자가 전달한 필터 조건으로 쿼리와 파라미터를 동적으로 생성
        IssueFilterQueryGenerator filterQueryGenerator = new IssueFilterQueryGenerator(issueQueryDto);
        Map<String, Object> filter = filterQueryGenerator.generate(labelId, milestoneId);
        return issueCustomRepository.findIssueWithFilter(filter, issueQueryDto);
    }

    private List<LabelCoverDto> getLabels(Long filterId) {
        List<Long> labelIds = issueQueryService.findLabelIdsByIssueId(filterId);
        if (!labelIds.isEmpty()) {
            return labelService.findLabelCoverDtoByIds(labelIds);
        }
        return List.of();
    }
}
