package com.issuetracker.issue.controller;

import com.issuetracker.issue.dto.IssueCountDto;
import com.issuetracker.issue.dto.IssueCreateRequestDto;
import com.issuetracker.issue.dto.IssueCreateResponseDto;
import com.issuetracker.issue.dto.IssueDetailDto;
import com.issuetracker.issue.dto.IssueTitleModifyDto;
import com.issuetracker.issue.service.IssueCudService;
import com.issuetracker.issue.service.IssueDetailService;
import com.issuetracker.issue.service.IssueQueryService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {
    private final IssueQueryService issueQueryService;
    private final IssueDetailService issueDetailService;
    private final IssueCudService issueCudService;

    @GetMapping("/count")
    public ResponseEntity<IssueCountDto> countIssues() {
        IssueCountDto issueCountDto = issueQueryService.countIssues();
        return ResponseEntity.ok().body(issueCountDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueDetailDto> showIssue(@PathVariable Long id) {
        IssueDetailDto issueDetailDto = issueDetailService.showIssue(id);
        return ResponseEntity.ok(issueDetailDto);
    }

    @PostMapping
    public ResponseEntity<IssueCreateResponseDto> createIssue(
            @Valid @RequestBody IssueCreateRequestDto issueCreateRequestDto) {
        IssueCreateResponseDto issue = issueCudService.createIssue(issueCreateRequestDto);
        URI location = URI.create(String.format("/api/issues/%s", issue.getId()));
        return ResponseEntity.created(location).body(issue);
    }

    @PatchMapping("/{id}/title")
    public ResponseEntity<Void> modifyIssueTitle(@Valid @RequestBody IssueTitleModifyDto issueTitleModifyDto,
                                                 @PathVariable Long id) {
        issueCudService.modifyIssueTitle(id, issueTitleModifyDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/body")
    public ResponseEntity<Void> modifyIssueBody(@Valid @RequestBody IssueBodyModifyDto issueBodyModifyDto,
                                                @PathVariable Long id) {
        issueCudService.modifyIssueBody(id, issueBodyModifyDto);
        return ResponseEntity.ok().build();
    }
}
