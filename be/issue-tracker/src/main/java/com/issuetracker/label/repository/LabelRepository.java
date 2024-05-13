package com.issuetracker.label.repository;

import com.issuetracker.label.domain.Label;
import org.springframework.data.repository.CrudRepository;

public interface LabelRepository extends CrudRepository<Label, Long> {
}