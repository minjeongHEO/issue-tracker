package com.issuetracker.label.repository;

import com.issuetracker.label.domain.Label;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomLabelRepositoryImpl implements CustomLabelRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Label insert(Label label) {
        String sql = "INSERT INTO label (name, description, background_color) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, label.getName(), label.getDescription(), label.getBgColor());
        return label;
    }
}
