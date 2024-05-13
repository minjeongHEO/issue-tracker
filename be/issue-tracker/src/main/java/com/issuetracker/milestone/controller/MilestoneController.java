package com.issuetracker.milestone.controller;

import com.issuetracker.milestone.domain.Milestone;
import com.issuetracker.milestone.dto.MilestoneCountDto;
import com.issuetracker.milestone.dto.MilestoneCreateDto;
import com.issuetracker.milestone.service.MilestoneService;
import jakarta.validation.Valid;
import java.net.URI;
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
@RequestMapping("/api/milestones")
public class MilestoneController {
    private final MilestoneService milestoneService;

    public MilestoneController(MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }

    @PostMapping
    public ResponseEntity<Milestone> createMilestone(@Valid @RequestBody MilestoneCreateDto milestoneCreateDto) {
        Milestone milestone = milestoneService.createMilestone(milestoneCreateDto);

        URI location = URI.create(String.format("/api/milestones/%s", milestone.getId()));
        return ResponseEntity.created(location).body(milestone);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Milestone> updateMilestone(@Valid @RequestBody MilestoneCreateDto milestoneCreateDto,
                                                     @PathVariable Long id) {
        Milestone milestone = milestoneService.modifyMilestone(milestoneCreateDto, id);

        URI location = URI.create(String.format("/api/milestones/%s", milestone.getId()));
        return ResponseEntity.created(location).body(milestone);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMilestone(@PathVariable Long id) {
        milestoneService.deleteMilestone(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public ResponseEntity<MilestoneCountDto> countMilestones() {
        MilestoneCountDto milestoneCountDto = milestoneService.countMilestones();
        return ResponseEntity.ok().body(milestoneCountDto);
    }
}
