package com.issuetracker.milestone.service;

import com.issuetracker.issue.service.IssueQueryService;
import com.issuetracker.milestone.Repository.MilestoneRepository;
import com.issuetracker.milestone.domain.Milestone;
import com.issuetracker.milestone.dto.MilestoneCountDto;
import com.issuetracker.milestone.dto.MilestoneCreateDto;
import com.issuetracker.milestone.dto.MilestoneDetailDto;
import com.issuetracker.milestone.dto.MilestoneListDto;
import com.issuetracker.milestone.dto.SimpleMilestoneDto;
import com.issuetracker.milestone.exception.MilestoneNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MilestoneService {
    private final MilestoneRepository milestoneRepository;
    private final IssueQueryService issueQueryService;

    @Transactional
    public Milestone createMilestone(MilestoneCreateDto milestoneCreateDto) {
        Milestone milestone = toMilestone(milestoneCreateDto);
        Milestone saved = milestoneRepository.save(milestone);
        log.info("새로운 마일스톤이 생성되었습니다. {}", saved);
        return saved;
    }

    @Transactional
    public Milestone modifyMilestone(MilestoneCreateDto milestoneCreateDto, Long id) {
        Milestone milestone = toMilestone(milestoneCreateDto);
        milestone.setId(id);
        Milestone modified = milestoneRepository.save(milestone);
        log.info("마일스톤이 수정되었습니다. {}", modified);
        return modified;
    }

    @Transactional
    public void deleteMilestone(Long id) {
        validateExists(id);
        milestoneRepository.deleteById(id);
        log.info("마일스톤이 삭제되었습니다. id = {}", id);
    }

    @Transactional(readOnly = true)
    public MilestoneCountDto countMilestones() {
        Long isClosed = milestoneRepository.countByIsClosed(true);
        Long isOpened = milestoneRepository.countByIsClosed(false);
        return new MilestoneCountDto(isOpened, isClosed);
    }

    @Transactional
    public void close(Long id) {
        validateExists(id);
        milestoneRepository.closeById(id);
        log.info("마일스톤이 닫혔습니다. id = {}", id);
    }

    @Transactional
    public void open(Long id) {
        validateExists(id);
        milestoneRepository.openById(id);
        log.info("마일스톤이 열렸습니다. id = {}", id);
    }

    @Transactional(readOnly = true)
    public MilestoneListDto showMilestoneList(boolean isClosed) {
        List<Milestone> milestones = milestoneRepository.findAllByIsClosed(isClosed);

        List<MilestoneDetailDto> milestoneDetailDtos = toMilestoneDetailDtos(milestones);
        return new MilestoneListDto(milestoneDetailDtos, (long) milestoneDetailDtos.size());
    }

    @Transactional(readOnly = true)
    public MilestoneDetailDto showMilestoneDetail(Long id) {
        Milestone milestone = getMilestone(id);

        Long openIssueCount = issueQueryService.countIssuesByMilestoneIdAndStatus(id, false);
        Long closedIssueCount = issueQueryService.countIssuesByMilestoneIdAndStatus(id, true);

        return toMilestoneDetailDto(milestone, openIssueCount, closedIssueCount);
    }

    @Transactional
    public SimpleMilestoneDto showMilestoneCover(Long id) {
        Milestone milestone = getMilestone(id);
        Long openIssueCount = issueQueryService.countIssuesByMilestoneIdAndStatus(id, false);
        Long closedIssueCount = issueQueryService.countIssuesByMilestoneIdAndStatus(id, true);
        return new SimpleMilestoneDto(milestone.getId(), milestone.getName(), openIssueCount, closedIssueCount);
    }

    @Transactional(readOnly = true)
    public Long findIdByName(String name) {
        return milestoneRepository.findIdByName(name);
    }

    private List<MilestoneDetailDto> toMilestoneDetailDtos(List<Milestone> milestones) {
        List<MilestoneDetailDto> milestoneDetailDtos = new ArrayList<>();
        for (Milestone milestone : milestones) {
            Long milestoneId = milestone.getId();
            Long openIssueCount = issueQueryService.countIssuesByMilestoneIdAndStatus(milestoneId, false);
            Long closedIssueCount = issueQueryService.countIssuesByMilestoneIdAndStatus(milestoneId, true);
            MilestoneDetailDto milestoneDetailDto = toMilestoneDetailDto(milestone, openIssueCount,
                    closedIssueCount);

            milestoneDetailDtos.add(milestoneDetailDto);
        }
        return milestoneDetailDtos;
    }

    private MilestoneDetailDto toMilestoneDetailDto(Milestone milestone, Long openIssueCount,
                                                    Long closedIssueCount) {
        return MilestoneDetailDto.builder()
                .id(milestone.getId())
                .name(milestone.getName())
                .description(milestone.getDescription())
                .dueDate(milestone.getDueDate())
                .openIssueCount(openIssueCount)
                .closedIssueCount(closedIssueCount)
                .isClosed(milestone.isClosed())
                .build();
    }

    private Milestone toMilestone(MilestoneCreateDto milestoneCreateDto) {
        return new Milestone(milestoneCreateDto.getName(), milestoneCreateDto.getDescription(),
                milestoneCreateDto.getDueDate(), false);
    }

    private void validateExists(Long id) {
        if (!milestoneRepository.existsById(id)) {
            throw new MilestoneNotFoundException();
        }
    }

    private Milestone getMilestone(Long id) {
        return milestoneRepository.findById(id)
                .orElseThrow(MilestoneNotFoundException::new);
    }
}
