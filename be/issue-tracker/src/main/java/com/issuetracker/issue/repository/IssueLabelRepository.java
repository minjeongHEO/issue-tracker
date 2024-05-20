package com.issuetracker.issue.repository;

import com.issuetracker.global.repository.WithInsert;
import com.issuetracker.issue.entity.IssueLabel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface IssueLabelRepository extends CrudRepository<IssueLabel, Long>, WithInsert<IssueLabel> {
    Optional<IssueLabel> findByIssueIdAndLabelId(Long issueId, Long labelId);

    @Query("SELECT label_id FROM issue_label where issue_id = :issueId")
    List<Long> findAllByIssueId(Long issueId);

    @Modifying
    @Query("DELETE FROM issue_label WHERE issue_id = :issueId AND label_id = :labelId")
    void deleteById(Long issueId, Long labelId);

    @Modifying
    @Query("DELETE FROM issue_label WHERE issue_id = :issueId")
    void deleteByIssueId(Long issueId);
}
