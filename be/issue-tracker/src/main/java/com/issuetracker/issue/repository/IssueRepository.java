package com.issuetracker.issue.repository;

import com.issuetracker.issue.domain.Issue;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface IssueRepository extends CrudRepository<Issue, Long> {

    Long countByMilestoneIdAndIsClosed(Long milestoneId, boolean isClosed);

    List<Issue> findAllByIsClosed(boolean isClosed);

    long countAllByIsClosed(boolean isClosed);

}
