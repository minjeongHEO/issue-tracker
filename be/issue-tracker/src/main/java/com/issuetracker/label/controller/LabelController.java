package com.issuetracker.label.controller;

import com.issuetracker.label.dto.LabelBgColorDto;
import com.issuetracker.label.dto.LabelCountDto;
import com.issuetracker.label.dto.LabelDto;
import com.issuetracker.label.dto.LabelListDto;
import com.issuetracker.label.entity.Label;
import com.issuetracker.label.service.LabelService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/labels")
@RequiredArgsConstructor
public class LabelController {
    private final LabelService labelService;

    @GetMapping
    public ResponseEntity<LabelListDto> getLabels() {
        long count = labelService.countLabels();
        List<Label> labels = labelService.getLabels();
        LabelListDto labelListDto = new LabelListDto(count, labels);
        return ResponseEntity.ok().body(labelListDto);
    }

    @PostMapping
    public ResponseEntity<Label> createLabels(@Valid @RequestBody LabelDto labelDto) {
        Label label = labelService.createLabel(labelDto);
        URI location = URI.create(String.format("/api/labels/%s", label.getId()));
        return ResponseEntity.created(location).body(label);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Label> modifyLabel(@Valid @RequestBody LabelDto labelDto, @PathVariable Long id) {
        Label label = labelService.modifyLabel(labelDto, id);
        return ResponseEntity.ok().body(label);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Label> deleteLabel(@PathVariable Long id) {
        labelService.deleteLabel(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/bgcolor")
    public ResponseEntity<LabelBgColorDto> refreshLabelBackgroundColor() {
        LabelBgColorDto labelBgColorDto = labelService.refreshLabelBackgroundColor();
        return ResponseEntity.ok().body(labelBgColorDto);
    }

    @GetMapping("/count")
    public ResponseEntity<LabelCountDto> countLabels() {
        LabelCountDto labelCountDto = new LabelCountDto(labelService.countLabels());
        return ResponseEntity.ok().body(labelCountDto);
    }
}
