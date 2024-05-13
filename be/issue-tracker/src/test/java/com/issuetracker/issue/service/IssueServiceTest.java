package com.issuetracker.issue.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.issuetracker.issue.domain.Issue;
import com.issuetracker.issue.dto.IssueListDto;
import com.issuetracker.issue.repository.IssueLabelRepository;
import com.issuetracker.issue.repository.IssueRepository;
import com.issuetracker.label.dto.LabelCoverDto;
import com.issuetracker.label.repository.LabelRepository;
import com.issuetracker.milestone.Repository.MilestoneRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class IssueServiceTest {
    @Mock
    private IssueRepository issueRepository;
    @Mock
    private IssueLabelRepository issueLabelRepository;
    @Mock
    private MilestoneRepository milestoneRepository;
    @Mock
    private LabelRepository labelRepository;
    @InjectMocks
    private IssueService issueService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("열린 이슈 목록을 조회할 수 있다.")
    @Test
    public void getOpenIssues() {
        LocalDateTime now = LocalDateTime.now();
        List<Issue> mockIssues = prepareMockIssues(now, "Issue1", "lucas", false, 1L);
        prepareMockRepositories(mockIssues, false);

        IssueListDto actual = createIssueListDto(1L, "Issue1", "lucas", now);
        List<IssueListDto> result = issueService.getIssuesByIsClosed(false);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).toString()).isEqualTo(actual.toString());

        // 이슈 Repository 및 레이블 Repository의 호출 검증
        verify(issueRepository, times(1)).findAllByIsClosed(false);
        verify(issueLabelRepository, times(1)).findAllByIssueId(1L);
        verify(labelRepository, times(0)).findLabelCoverDtoById(any());
    }

    @DisplayName("닫힌 이슈 목록을 조회할 수 있다.")
    @Test
    public void getClosedIssues() {
        LocalDateTime now = LocalDateTime.now();
        List<Issue> mockIssues = prepareMockIssues(now, "Issue2", "tomas", true, 2L);
        prepareMockRepositories(mockIssues, true);

        IssueListDto actual = createIssueListDto(2L, "Issue2", "tomas", now);
        List<IssueListDto> result = issueService.getIssuesByIsClosed(true);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).toString()).isEqualTo(actual.toString());

        // 이슈 Repository 및 레이블 Repository의 호출 검증
        verify(issueRepository, times(1)).findAllByIsClosed(true);
        verify(issueLabelRepository, times(1)).findAllByIssueId(2L);
        verify(labelRepository, times(0)).findLabelCoverDtoById(any());
    }

    private List<Issue> prepareMockIssues(LocalDateTime now, String title, String memberId, boolean isClosed, Long id) {
        List<Issue> mockIssues = new ArrayList<>();
        Issue issue = new Issue(title, "내용입니다.", now, isClosed, memberId, null, null);
        issue.setId(id);
        mockIssues.add(issue);
        return mockIssues;
    }

    private List<LabelCoverDto> prepareMockLabelCovers() {
        List<LabelCoverDto> mockLabelCovers = new ArrayList<>();
        LabelCoverDto labelCover = new LabelCoverDto("검정", "#000000");
        mockLabelCovers.add(labelCover);
        return mockLabelCovers;
    }

    private void prepareMockRepositories(List<Issue> mockIssues, boolean isClosed) {
        when(issueRepository.findAllByIsClosed(isClosed)).thenReturn(mockIssues);
        when(issueLabelRepository.findAllByIssueId(anyLong())).thenReturn(new ArrayList<>());
        when(labelRepository.findLabelCoverDtoById(any())).thenReturn(prepareMockLabelCovers());
        when(milestoneRepository.findById(anyLong())).thenReturn(Optional.empty());
    }

    private IssueListDto createIssueListDto(long id, String title, String memberId, LocalDateTime createDate) {
        return IssueListDto.builder()
                .id(id)
                .title(title)
                .memberId(memberId)
                .createDate(createDate)
                .labels(null)
                .milestoneName(null)
                .build();
    }
}
