package com.issuetracker.global.repository;

import org.springframework.data.jdbc.core.JdbcAggregateOperations;

public interface WithInsert<T> {
    JdbcAggregateOperations getJdbcAggregateOperations();

    default T insert(T t) {
        return getJdbcAggregateOperations().insert(t);
    }
}
