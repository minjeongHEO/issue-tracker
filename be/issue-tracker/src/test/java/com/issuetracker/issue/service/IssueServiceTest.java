package com.issuetracker.issue.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.issuetracker.issue.dto.IssueCountDto;
import com.issuetracker.issue.repository.IssueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class IssueServiceTest {
    @Mock
    private IssueRepository issueRepository;
    @InjectMocks
    private IssueService issueService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("열린 이슈와 닫힌 이슈의 개수를 가져올 수 있다.")
    @Test
    public void testGetIssueCountDto() {
        long openedIssueCount = 3;
        long closedIssueCount = 2;

        // 열린 이슈 목록과 닫힌 이슈 목록 설정
        when(issueRepository.countAllByIsClosed(false)).thenReturn(openedIssueCount);
        when(issueRepository.countAllByIsClosed(true)).thenReturn(closedIssueCount);

        IssueCountDto issueCountDto = issueService.countIssues();

        assertThat(issueCountDto.getOpenedIssueCount()).isEqualTo(openedIssueCount);
        assertThat(issueCountDto.getClosedIssueCount()).isEqualTo(closedIssueCount);
    }
}
