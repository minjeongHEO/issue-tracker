package com.issuetracker.issue.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.issuetracker.issue.entity.IssueAssignee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class IssueAssigneeRepositoryTest {
    @Autowired
    IssueAssigneeRepository issueAssigneeRepository;

    @BeforeEach
    void setUp() {
        issueAssigneeRepository.deleteAll();
    }

    @Test
    void crud() {
        issueAssigneeRepository.insert(new IssueAssignee(1L, "sangchu"));
        IssueAssignee find = issueAssigneeRepository.findByIssueIdAndMemberId(1L, "sangchu").get();
        assertThat(find.getIssueId()).isEqualTo(1L);
        assertThat(find.getMemberId()).isEqualTo("sangchu");

        issueAssigneeRepository.deleteById(1L, "sangchu");
        assertThat(issueAssigneeRepository.count()).isEqualTo(0);
    }
}