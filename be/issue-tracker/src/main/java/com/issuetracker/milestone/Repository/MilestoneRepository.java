package com.issuetracker.milestone.Repository;

import com.issuetracker.milestone.domain.Milestone;
import org.springframework.data.repository.CrudRepository;

public interface MilestoneRepository extends CrudRepository<Milestone, Long> {
}
