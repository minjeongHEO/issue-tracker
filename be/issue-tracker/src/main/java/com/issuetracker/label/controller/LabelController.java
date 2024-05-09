package com.issuetracker.label.controller;

import com.issuetracker.label.domain.Label;
import com.issuetracker.label.dto.LabelDto;
import com.issuetracker.label.service.LabelService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/labels")
@RequiredArgsConstructor
public class LabelController {
    private final LabelService labelService;

    @PostMapping
    public ResponseEntity<Label> createLabels(@Valid @RequestBody LabelDto labelDto) {
        Label label = labelService.createLabel(labelDto);
        URI location = URI.create(String.format("/api/labels/%s", label.getId()));
        return ResponseEntity.created(location).body(label);
    }
}
