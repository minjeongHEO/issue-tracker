package com.issuetracker.milestone.service;

import com.issuetracker.issue.repository.IssueRepository;
import com.issuetracker.milestone.Repository.MilestoneRepository;
import com.issuetracker.milestone.domain.Milestone;
import com.issuetracker.milestone.dto.MilestoneCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MilestoneService {
    private final MilestoneRepository milestoneRepository;
    private final IssueRepository issueRepository;

    public Milestone createMilestone(MilestoneCreateDto milestoneCreateDto) {
        Milestone milestone = toMilestone(milestoneCreateDto);
        Milestone saved = milestoneRepository.save(milestone);
        log.info("새로운 마일스톤이 생성되었습니다. {}", saved);
        return saved;
    }

    private Milestone toMilestone(MilestoneCreateDto milestoneCreateDto) {
        return new Milestone(milestoneCreateDto.getName(), milestoneCreateDto.getDescription(),
                milestoneCreateDto.getDueDate(), false);
    }
}
