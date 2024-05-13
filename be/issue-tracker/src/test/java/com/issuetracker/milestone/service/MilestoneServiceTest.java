package com.issuetracker.milestone.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.issuetracker.milestone.Repository.MilestoneRepository;
import com.issuetracker.milestone.domain.Milestone;
import com.issuetracker.milestone.dto.MilestoneCountDto;
import com.issuetracker.milestone.dto.MilestoneCreateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    @DisplayName("마일스톤을 성공적으로 생성하면 생성된 객체를 반환한다.")
    void create() {
        MilestoneCreateDto milestoneCreateDto = new MilestoneCreateDto("1번", "", null);
        Milestone milestone = new Milestone("1번", "", null, false);
        when(milestoneRepository.save(any(Milestone.class))).thenReturn(milestone);

        Milestone created = milestoneService.createMilestone(milestoneCreateDto);

        assertThat(milestoneCreateDto.getName()).isEqualTo(created.getName());
        assertThat(milestoneCreateDto.getDescription()).isEqualTo(created.getDescription());
        assertThat(milestoneCreateDto.getDueDate()).isEqualTo(created.getDueDate());
    }

    @Test
    @DisplayName("마일스톤이 수정되면 수정된 마일스톤을 반환한다.")
    void modifyMilestone() {
        // Given
        Long milestoneId = 1L;
        MilestoneCreateDto milestoneCreateDto = new MilestoneCreateDto("New Title", "New Description", null);
        Milestone milestone = new Milestone("milestoneCreateDto.getName()", milestoneCreateDto.getDescription(), null,
                false);
        milestone.setId(milestoneId);

        when(milestoneRepository.save(any(Milestone.class))).thenReturn(milestone);

        // When
        Milestone modified = milestoneService.modifyMilestone(milestoneCreateDto, milestoneId);

        // Then
        assertThat(modified).isNotNull();
        assertThat(milestoneId).isEqualTo(modified.getId());
        assertThat("New Title").isEqualTo(modified.getName());
    }

    @Test
    @DisplayName("아이디가 존재하면 마일스톤을 삭제할 수 있다.")
    void deleteMilestone() {
        // Given
        Long milestoneId = 1L;

        doNothing().when(milestoneRepository).deleteById(milestoneId);
        when(milestoneRepository.existsById(milestoneId)).thenReturn(true);
        // When
        milestoneService.deleteMilestone(milestoneId);

        // Then
        verify(milestoneRepository).deleteById(milestoneId);
    }

    @Test
    @DisplayName("마일스톤의 개수를 카운트할 수 있다.")
    void CountMilestones() {
        // Given
        when(milestoneRepository.countByIsClosed(true)).thenReturn(5L);
        when(milestoneRepository.countByIsClosed(false)).thenReturn(10L);

        // When
        MilestoneCountDto result = milestoneService.countMilestones();

        // Then
        assertThat(result.getIsOpened()).isEqualTo(10L);
        assertThat(result.getIsClosed()).isEqualTo(5L);

        verify(milestoneRepository).countByIsClosed(true);
        verify(milestoneRepository).countByIsClosed(false);
    }
}