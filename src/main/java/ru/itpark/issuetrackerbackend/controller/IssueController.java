package ru.itpark.issuetrackerbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itpark.issuetrackerbackend.domain.Issue;
import ru.itpark.issuetrackerbackend.service.IssueService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;

    @GetMapping
    public List<Issue> getIssues() {
        return issueService.getIssues();
    }

    @PostMapping
    public void saveIssue(@RequestBody Issue issue) {
        issueService.saveIssue(issue);
    }
}
