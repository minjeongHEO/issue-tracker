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

    /**
     * 사용자가 입력한 정보를 바탕으로 새로운 마일스톤을 생성하여 저장한다.
     */
    @Transactional
    public Milestone createMilestone(MilestoneCreateDto milestoneCreateDto) {
        Milestone milestone = toMilestone(milestoneCreateDto);
        Milestone saved = milestoneRepository.save(milestone);
        log.info("새로운 마일스톤이 생성되었습니다. {}", saved);
        return saved;
    }

    /**
     * id와 일치하는 마일스톤의 정보를 수정한다. 일치하는 id가 없다면 예외를 발생시킨다.
     */
    @Transactional
    public Milestone modifyMilestone(MilestoneCreateDto milestoneCreateDto, Long id) {
        Milestone milestone = toMilestone(milestoneCreateDto);
        milestone.setId(id);
        Milestone modified = milestoneRepository.save(milestone);
        log.info("마일스톤이 수정되었습니다. {}", modified);
        return modified;
    }

    /**
     * id와 일치하는 마일스톤을 삭제한다. 일치하는 id가 없다면 예외를 발생시킨다.
     */
    @Transactional
    public void deleteMilestone(Long id) {
        validateExists(id);
        milestoneRepository.deleteById(id);
        log.info("마일스톤이 삭제되었습니다. id = {}", id);
    }

    /**
     * 모든 마일스톤의 개수를 열림, 닫힘으로 구분하여 카운트한다.
     */
    @Transactional(readOnly = true)
    public MilestoneCountDto countMilestones() {
        Long isClosed = milestoneRepository.countByIsClosed(true);
        Long isOpened = milestoneRepository.countByIsClosed(false);
        return new MilestoneCountDto(isOpened, isClosed);
    }

    /**
     * id와 일치하는 마일스톤을 닫는다. 일치하는 마일스톤이 없다면 예외를 발생시킨다.
     */
    @Transactional
    public void close(Long id) {
        validateExists(id);
        milestoneRepository.closeById(id);
        log.info("마일스톤이 닫혔습니다. id = {}", id);
    }

    /**
     * id와 일치하는 마일스톤을 연다. 일치하는 마일스톤이 없다면 예외를 발생시킨다.
     */
    @Transactional
    public void open(Long id) {
        validateExists(id);
        milestoneRepository.openById(id);
        log.info("마일스톤이 열렸습니다. id = {}", id);
    }

    /**
     * 저장된 모든 마일스톤의 정보를 반환한다.
     */
    @Transactional(readOnly = true)
    public MilestoneListDto showMilestoneList(boolean isClosed) {
        List<Milestone> milestones = milestoneRepository.findAllByIsClosed(isClosed);

        List<MilestoneDetailDto> milestoneDetailDtos = toMilestoneDetailDtos(milestones);
        return new MilestoneListDto((long) milestoneDetailDtos.size(), milestoneDetailDtos);
    }

    /**
     * id와 일치하는 마일스톤 정보를 찾아 반환한다. id와 일치하는 마일스톤이 없다면 에외를 발생시킨다.
     */
    @Transactional(readOnly = true)
    public MilestoneDetailDto showMilestoneDetail(Long id) {
        Milestone milestone = getMilestoneOrThrow(id);

        Long openIssueCount = issueQueryService.countIssuesByMilestoneIdAndStatus(id, false);
        Long closedIssueCount = issueQueryService.countIssuesByMilestoneIdAndStatus(id, true);

        return toMilestoneDetailDto(milestone, openIssueCount, closedIssueCount);
    }

    /**
     * id와 일치하는 마일스톤의 간략한 정보를 반환한다. id와 일치하는 마일스톤이 없다면 예외를 발생시킨다.
     */
    @Transactional
    public SimpleMilestoneDto showMilestoneCover(Long id) {
        Milestone milestone = getMilestoneOrThrow(id);
        Long openIssueCount = issueQueryService.countIssuesByMilestoneIdAndStatus(id, false);
        Long closedIssueCount = issueQueryService.countIssuesByMilestoneIdAndStatus(id, true);
        return new SimpleMilestoneDto(milestone.getId(), milestone.getName(), openIssueCount, closedIssueCount);
    }

    /**
     * 마일스톤의 이름과 일치하는 id를 찾아 반환한다.
     */
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

    private Milestone getMilestoneOrThrow(Long id) {
        return milestoneRepository.findById(id)
                .orElseThrow(MilestoneNotFoundException::new);
    }
}
