package com.issuetracker.issue.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.issuetracker.issue.domain.IssueLabel;
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
        issueLabelRepository.insert(1L, 4L);
        IssueLabel find = issueLabelRepository.findById(1L, 4L).get();

        assertThat(find.getIssueId()).isEqualTo(1L);
        assertThat(find.getLabelId()).isEqualTo(4L);

        issueLabelRepository.deleteById(1L, 4L);
        assertThat(issueLabelRepository.count()).isEqualTo(0);
    }
}