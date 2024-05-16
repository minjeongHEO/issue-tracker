package com.issuetracker.issue.controller;

import com.issuetracker.issue.dto.IssueCountDto;
import com.issuetracker.issue.dto.IssueDetailDto;
import com.issuetracker.issue.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;

    @GetMapping("/count")
    public ResponseEntity<IssueCountDto> countIssues() {
        IssueCountDto issueCountDto = issueService.countIssues();
        return ResponseEntity.ok().body(issueCountDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueDetailDto> showIssue(@PathVariable Long id) {
        IssueDetailDto issueDetailDto = issueService.showIssue(id);
        return ResponseEntity.ok(issueDetailDto);
    }
}
