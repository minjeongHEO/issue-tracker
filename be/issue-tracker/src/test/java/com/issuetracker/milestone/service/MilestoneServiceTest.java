package com.issuetracker.milestone.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.issuetracker.issue.service.IssueQueryService;
import com.issuetracker.milestone.Repository.MilestoneRepository;
import com.issuetracker.milestone.dto.MilestoneCountDto;
import com.issuetracker.milestone.dto.MilestoneCreateDto;
import com.issuetracker.milestone.entity.Milestone;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MilestoneServiceTest {
    @Mock
    MilestoneRepository milestoneRepository;
    @Mock
    IssueQueryService issueQueryService;

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
        Milestone milestone = new Milestone(null, "1번", "", null, false);
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
        Milestone milestone = new Milestone(milestoneId, milestoneCreateDto.getName(),
                milestoneCreateDto.getDescription(), null,
                false);

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

    @Test
    @DisplayName("마일스톤을 닫을 수 있다.")
    void closeMilestone() {
        // 준비
        Long id = 1L;
        when(milestoneRepository.existsById(id)).thenReturn(true);
        doNothing().when(milestoneRepository).closeById(id);

        // 실행
        milestoneService.close(id);

        // 검증
        verify(milestoneRepository).closeById(id);
    }

    @Test
    @DisplayName("마일스톤을 열 수 있다.")
    void openMilestoneTest() {
        // 준비
        Long id = 1L;
        when(milestoneRepository.existsById(id)).thenReturn(true);
        doNothing().when(milestoneRepository).openById(id);

        // 실행
        milestoneService.open(id);

        // 검증
        verify(milestoneRepository).openById(id);
    }

    @Test
    @DisplayName("모든 마일스톤 및 마일스톤에 종속된 이슈의 개수를 알 수 있다. ")
    void showMilestoneList() {
        // 준비
        Milestone milestone = new Milestone(null, "마일스톤", "설명", LocalDateTime.now(), false);
        when(milestoneRepository.findAllByIsClosed(false)).thenReturn(List.of(milestone));
        when(issueQueryService.countIssuesByMilestoneIdAndStatus(isNull(), eq(true))).thenReturn(5L);
        when(issueQueryService.countIssuesByMilestoneIdAndStatus(isNull(), eq(false))).thenReturn(3L);

        // 실행
        var listDto = milestoneService.showMilestoneList(false);

        // 검증
        assertThat(listDto.getMilestoneDetailDtos()).hasSize(1);
        assertThat(listDto.getMilestoneDetailDtos().get(0).getClosedIssueCount()).isEqualTo(5L);
        assertThat(listDto.getMilestoneDetailDtos().get(0).getOpenIssueCount()).isEqualTo(3L);

        // Mockito 검증
        verify(issueQueryService).countIssuesByMilestoneIdAndStatus(isNull(), eq(false));
        verify(issueQueryService).countIssuesByMilestoneIdAndStatus(isNull(), eq(true));
    }
}