package com.issuetracker.issue.repository;

import com.issuetracker.issue.domain.Issue;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface IssueRepository extends CrudRepository<Issue, Long> {

    Long countByMilestoneIdAndIsClosed(Long milestoneId, boolean isClosed);

    long countAllByIsClosed(boolean isClosed);

    @Modifying
    @Query("UPDATE issue SET title = :title WHERE id = :id")
    boolean updateTitleById(Long id, String title);

    @Modifying
    @Query("UPDATE issue SET content = :content, file_id = :fileId WHERE id = :id")
    boolean updateBodyById(Long id, String content, Long fileId);

    @Modifying
    @Query("UPDATE issue SET milestone_id = :milestoneId WHERE id = :id")
    void updateMilestoneById(Long id, Long milestoneId);
}
