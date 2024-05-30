package com.issuetracker.global.controller;

import com.issuetracker.global.dto.HomeComponentResponse;
import com.issuetracker.global.dto.HomeIssueResponse;
import com.issuetracker.global.service.HomeService;
import com.issuetracker.issue.dto.IssueQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    @GetMapping("/components")
    public ResponseEntity<HomeComponentResponse> getComponents() {
        HomeComponentResponse homeComponentResponse = homeService.getComponents();
        return ResponseEntity.ok().body(homeComponentResponse);
    }

    @GetMapping("/issues")
    public ResponseEntity<HomeIssueResponse> getFilteredIssues(@ModelAttribute IssueQueryDto issueQueryDto) {
        HomeIssueResponse homeIssueResponse = homeService.getFilteredIssues(issueQueryDto);
        return ResponseEntity.ok().body(homeIssueResponse);
    }
}
