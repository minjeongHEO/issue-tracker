package com.issuetracker.global.controller;

import com.issuetracker.global.dto.HomeResponseDto;
import com.issuetracker.global.service.HomeService;
import com.issuetracker.issue.dto.IssueFilterResponseDto;
import com.issuetracker.issue.dto.IssueQueryDto;
import com.issuetracker.issue.service.IssueService;
import java.util.List;
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
    private final IssueService issueService;

    @GetMapping("/issues")
    public ResponseEntity<HomeResponseDto> getFilteredIssues(@ModelAttribute IssueQueryDto issueQueryDto) {
        List<IssueFilterResponseDto> issueFilterResponses = homeService.getFilteredIssues(issueQueryDto);
        HomeResponseDto homeResponseDto = new HomeResponseDto(issueService.countIssues(), issueFilterResponses);
        return ResponseEntity.ok().body(homeResponseDto);
    }
}
