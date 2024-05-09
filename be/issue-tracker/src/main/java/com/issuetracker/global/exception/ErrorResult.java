package com.issuetracker.global.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class ErrorResult {
    private final Map<String, Object> errors = new HashMap<>();

    public void add(String key, Object value) {
        errors.put(key, value);
    }
}
