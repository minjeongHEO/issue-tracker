package com.issuetracker.issue.repository;

import com.issuetracker.issue.domain.IssueAssignee;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface IssueAssigneeRepository extends CrudRepository<IssueAssignee, Long> {
    @Modifying
    @Query("INSERT INTO issue_assignee (issue_id, member_id) VALUES (:issueId, :memberId)")
    void insert(Long issueId, String memberId);

    Optional<IssueAssignee> findByIssueIdAndMemberId(Long issueId, String memberId);

    List<IssueAssignee> findAllByIssueId(Long issueId);

    @Query("SELECT member_id FROM issue_assignee WHERE issue_id = :issueId")
    List<String> findAssigneeIdsByIssueId(Long issueId);

    @Modifying
    @Query("DELETE FROM issue_assignee WHERE issue_id = :issueId AND member_id = :memberId")
    void deleteById(Long issueId, String memberId);
}
