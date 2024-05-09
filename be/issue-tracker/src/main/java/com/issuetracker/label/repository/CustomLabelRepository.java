package com.issuetracker.label.repository;

import com.issuetracker.label.domain.Label;

public interface CustomLabelRepository {
    Label insert(Label label);
}
