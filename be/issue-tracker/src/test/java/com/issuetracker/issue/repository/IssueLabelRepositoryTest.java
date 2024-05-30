package com.issuetracker.issue.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.issuetracker.issue.entity.IssueLabel;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class IssueLabelRepositoryTest {
    @Autowired
    IssueLabelRepository issueLabelRepository;

    @BeforeEach
    void setUp() {
        issueLabelRepository.deleteAll();
    }

    @Test
    void crud() {
        issueLabelRepository.insert(new IssueLabel(2L, 5L));
        issueLabelRepository.insert(new IssueLabel(2L, 6L));

        IssueLabel find = issueLabelRepository.findByIssueIdAndLabelId(2L, 5L).get();

        assertThat(find.getIssueId()).isEqualTo(2L);
        assertThat(find.getLabelId()).isEqualTo(5L);

        List<Long> allByIssueId = issueLabelRepository.findAllByIssueId(2L);

        assertThat(allByIssueId.size()).isEqualTo(2);
        assertThat(allByIssueId.get(0)).isEqualTo(5L);
        assertThat(allByIssueId.get(1)).isEqualTo(6L);

        issueLabelRepository.deleteById(2L, 5L);
        assertThat(issueLabelRepository.count()).isEqualTo(1);
    }
}