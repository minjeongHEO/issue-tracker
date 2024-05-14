package com.issuetracker.milestone.service;

import com.issuetracker.issue.repository.IssueRepository;
import com.issuetracker.milestone.Repository.MilestoneRepository;
import com.issuetracker.milestone.domain.Milestone;
import com.issuetracker.milestone.dto.MilestoneCountDto;
import com.issuetracker.milestone.dto.MilestoneCreateDto;
import com.issuetracker.milestone.exception.MilestoneNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MilestoneService {
    private final MilestoneRepository milestoneRepository;
    private final IssueRepository issueRepository;

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

    private Milestone toMilestone(MilestoneCreateDto milestoneCreateDto) {
        return new Milestone(milestoneCreateDto.getName(), milestoneCreateDto.getDescription(),
                milestoneCreateDto.getDueDate(), false);
    }

    private void validateExists(Long id) {
        if (!milestoneRepository.existsById(id)) {
            throw new MilestoneNotFoundException();
        }
    }
}
