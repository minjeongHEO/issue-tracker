package com.issuetracker.label.repository;

import com.issuetracker.label.domain.Label;
import com.issuetracker.label.dto.LabelCoverDto;
import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface LabelRepository extends CrudRepository<Label, Long> {
    @Query("SELECT name, background_color FROM label WHERE id IN (:ids)")
    List<LabelCoverDto> findLabelCoverDtoByIds(List<Long> ids);
}