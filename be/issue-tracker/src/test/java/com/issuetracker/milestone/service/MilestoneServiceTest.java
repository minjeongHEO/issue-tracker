package com.issuetracker.milestone.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.issuetracker.milestone.Repository.MilestoneRepository;
import com.issuetracker.milestone.domain.Milestone;
import com.issuetracker.milestone.dto.MilestoneCreateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class MilestoneServiceTest {
    @Mock
    MilestoneRepository milestoneRepository;
    @InjectMocks
    MilestoneService milestoneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create() {
        MilestoneCreateDto milestoneCreateDto = new MilestoneCreateDto("1번", "", null);
        Milestone milestone = new Milestone("1번", "", null, false);
        when(milestoneRepository.save(Mockito.any(Milestone.class))).thenReturn(milestone);

        Milestone created = milestoneService.createMilestone(milestoneCreateDto);

        assertThat(milestoneCreateDto.getName()).isEqualTo(created.getName());
        assertThat(milestoneCreateDto.getDescription()).isEqualTo(created.getDescription());
        assertThat(milestoneCreateDto.getDueDate()).isEqualTo(created.getDueDate());
    }
}