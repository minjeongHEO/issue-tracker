package com.issuetracker.global.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class WithInsertImpl<T> implements WithInsert<T> {
    private final JdbcAggregateOperations jdbcAggregateOperations;

    @Override
    public JdbcAggregateOperations getJdbcAggregateOperations() {
        return this.jdbcAggregateOperations;
    }
}
