package com.issuetracker.milestone.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.issuetracker.milestone.domain.Milestone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MilestoneRepositoryTest {
    @Autowired
    MilestoneRepository milestoneRepository;

    @BeforeEach
    void setUp() {
        milestoneRepository.deleteAll();
    }

    @Test
    @DisplayName("isClosed 변수 여부에 따라 마일스톤 개수를 조회할 수 있다.")
    void countByIsClosed() {
        milestoneRepository.save(new Milestone("1", null, null, false));
        milestoneRepository.save(new Milestone("2", null, null, false));
        milestoneRepository.save(new Milestone("3", null, null, true));

        assertThat(milestoneRepository.countByIsClosed(false)).isEqualTo(2);
        assertThat(milestoneRepository.countByIsClosed(true)).isEqualTo(1);
    }

    @Test
    void closeById() {
        Milestone saved = milestoneRepository.save(new Milestone("1", null, null, false));
        milestoneRepository.closeById(saved.getId());
        Milestone milestone = milestoneRepository.findById(saved.getId()).get();

        assertThat(milestone.isClosed()).isEqualTo(true);
    }
}