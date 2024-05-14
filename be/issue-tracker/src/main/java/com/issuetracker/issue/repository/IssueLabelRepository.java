package com.issuetracker.issue.repository;

import com.issuetracker.issue.domain.IssueLabel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface IssueLabelRepository extends CrudRepository<IssueLabel, Long> {
    @Modifying
    @Query("INSERT INTO issue_label (issue_id, label_id) VALUES (:issueId, :labelId)")
    void insert(Long issueId, Long labelId);

    Optional<IssueLabel> findByIssueIdAndLabelId(Long issueId, Long labelId);

    @Query("SELECT label_id FROM issue_label where issue_id = :issueId")
    List<Long> findAllByIssueId(Long issueId);

    @Modifying
    @Query("DELETE FROM issue_label WHERE issue_id = :issueId AND label_id = :labelId")
    void deleteById(Long issueId, Long labelId);
}
