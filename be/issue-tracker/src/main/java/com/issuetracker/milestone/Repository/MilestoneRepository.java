package com.issuetracker.milestone.Repository;

import com.issuetracker.milestone.domain.Milestone;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface MilestoneRepository extends CrudRepository<Milestone, Long> {
    Long countByIsClosed(boolean isClosed);

    @Modifying
    @Query("UPDATE milestone SET is_closed = true WHERE id = :id")
    void closeById(Long id);

    @Modifying
    @Query("UPDATE milestone SET is_closed = false WHERE id = :id")
    void openById(Long id);
}
