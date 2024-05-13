package com.issuetracker.issue.controller;

import com.issuetracker.issue.dto.IssueCountDto;
import com.issuetracker.issue.dto.IssueListDto;
import com.issuetracker.issue.service.IssueService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;

    @GetMapping
    public ResponseEntity<List<IssueListDto>> getIssuesByIsClosed(@RequestParam("isClosed") boolean isClosed) {
        List<IssueListDto> issueListDto = issueService.getIssuesByIsClosed(isClosed);
        return ResponseEntity.ok().body(issueListDto);
    }

    @GetMapping("/count")
    public ResponseEntity<IssueCountDto> getCounts() {
        IssueCountDto issueCountDto = issueService.getIssueCountDto();
        return ResponseEntity.ok().body(issueCountDto);
    }
}
